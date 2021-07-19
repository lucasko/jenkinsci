import com.synopsys.arc.jenkins.plugins.rolestrategy.RoleType
import com.michelin.cio.hudson.plugins.rolestrategy.Role
import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import com.michelin.cio.hudson.plugins.rolestrategy.*
import groovy.util.logging.Slf4j
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.Permission
import javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration
import jenkins.model.GlobalConfiguration
import jenkins.security.s2m.AdminWhitelistRule
import jenkins.model.Jenkins
import org.jenkinsci.plugins.saml.IdpMetadataConfiguration
import org.jenkinsci.plugins.saml.SamlSecurityRealm

import java.lang.reflect.Constructor
import groovy.json.JsonSlurper

@Slf4j
class Accounts {

    private static String VERSION="2021/04/18 14h10"

    def permissions=[
            admin:[
                    "hudson.model.View.Delete",
                    "hudson.model.View.Configure",
                    "hudson.model.View.Read",
                    "hudson.model.View.Create",

                    "hudson.model.Computer.Connect",
                    "hudson.model.Computer.Create",
                    "hudson.model.Computer.Build",
                    "hudson.model.Computer.Delete",
                    "hudson.model.Computer.Provision",
                    "hudson.model.Computer.Configure",
                    "hudson.model.Computer.Disconnect",

                    "hudson.model.Run.Delete",
                    "hudson.model.Run.Update",

                    "hudson.model.Hudson.UploadPlugins",
                    "hudson.model.Hudson.ConfigureUpdateCenter",
                    "hudson.model.Hudson.Administer",
                    "hudson.model.Hudson.Read",
                    "hudson.model.Hudson.RunScripts",

                    "com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains",

                    "hudson.model.Item.Configure",
                    "hudson.model.Item.Cancel",
                    "hudson.model.Item.Read",
                    "hudson.model.Item.Build",
                    "hudson.model.Item.Create",
                    "hudson.model.Item.Move",
                    "hudson.model.Item.Workspace",
                    "hudson.model.Item.Delete",
                    "hudson.model.Item.Discover",

                    "hudson.scm.SCM.Tag",

                    "com.cloudbees.plugins.credentials.CredentialsProvider.View",
                    "com.cloudbees.plugins.credentials.CredentialsProvider.Update",
                    "com.cloudbees.plugins.credentials.CredentialsProvider.Delete",
                    "com.cloudbees.plugins.credentials.CredentialsProvider.Create",
            ],

    ]

    def roles = [
            global : [

                        [
                                name : "admin",
                                users: [
                                        "admin",
                                ],
                                perm : permissions.admin,
                        ],

                        [
                                name : "authenticated",
                                users: [
                                        "authenticated",
                                ],
                                perm : [
                                        "hudson.model.Hudson.Read"
                                ],
                        ],

                        [
                                name : "anonymous",
                                users: [
                                        "anonymous",
                                ],
                                perm : [
                                        "hudson.model.Hudson.Read"
                                ],
                        ],
            ],

            project: [

                    [
                            name : "authenticated",
                            users: [
                                    "authenticated",
                            ],
                            perm : [
                                    "hudson.model.Hudson.Read",
                                    "hudson.model.Item.Read",
                                    "hudson.model.Item.Workspace"
                            ],
                            pattern: "myjob1.*|myjob2.*",
                    ],

                    [
                            name : "anonymous",
                            users: [
                                    "anonymous",
                            ],
                            perm : [
                                    "hudson.model.Hudson.Read",
                                    "hudson.model.Item.Read",
                                    "hudson.model.Item.Workspace"
                            ],
                            pattern: "myjob1.*|myjob2.*",
                    ],

                        [
                                name : "qa",
                                users: [

                                        "a@example.com",
                                        "b@example.com",
                                        "qa",
                                ],
                                perm :[
                                        "hudson.model.Item.Read",
                                        "hudson.model.Item.Build",
                                        "hudson.model.Item.Workspace",
                                        "hudson.model.Item.Discover",
                                        "hudson.model.Item.Cancel",
                                        "hudson.model.Item.Configure",
                                        "hudson.model.Run.Update",
                                ],
                                pattern: "qaJobs.*",
                        ],
            ]
    ]

    static void main(String... args) {
        log.info "Accounts.groovy  starting  version=$Accounts.VERSION..."

        def accounts = new Accounts()
        accounts.setup()

        log.info "Accounts.groovy  end"
    }

    void setup() {

        def instance = Jenkins.getInstance()
        def strategy = new RoleBasedAuthorizationStrategy()

        //Make role-strategy plugin methods accessible
        Constructor[] constrs = Role.class.getConstructors()
        for (Constructor<?> c : constrs) {
            c.setAccessible(true)
        }

        def realm
        realm = createUsers( strategy)

        createRoles(strategy)




        instance.setSecurityRealm(realm)
        instance.setAuthorizationStrategy(strategy)
        instance.save()




//        Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)
//
//        log.info "--> disabling scripts security for job dsl scripts"
//
//        GlobalConfiguration.all().get(GlobalJobDslSecurityConfiguration.class).useScriptSecurity = false
    }

    private def createUsers( RoleBasedAuthorizationStrategy strategy) {
        def hudsonRealm = new HudsonPrivateSecurityRealm(false)

        log.info "creating admin user .."
        hudsonRealm.createAccount("admin", "1234")

        log.info "creating qa user .."
        hudsonRealm.createAccount("qa", "1234")

        return hudsonRealm
    }


    private createRoles(RoleBasedAuthorizationStrategy strategy) {

        roles.global.each{role->
            Set<Permission> permissions = new HashSet<>()
            role.perm.each{perm->
                permissions.add(Permission.fromId(perm))
            }
            strategy.addRole(RoleType.fromString(RoleBasedAuthorizationStrategy.GLOBAL)   , new Role(role.name, role.pattern ?: ".*", permissions))

            role.users.each{user->
                strategy.doAssignRole(RoleBasedAuthorizationStrategy.GLOBAL, role.name, user)
            }
        }


        roles.project.each{role->
            Set<Permission> permissions = new HashSet<>()
            role.perm.each{perm->
                permissions.add(Permission.fromId(perm))
            }

            println "RoleBasedAuthorizationStrategy.PROJECT="+RoleBasedAuthorizationStrategy.PROJECT
            strategy.addRole(RoleType.fromString(RoleBasedAuthorizationStrategy.PROJECT) ,  new Role(role.name, role.pattern ?: ".*", permissions))

            role.users.each{user->
                strategy.doAssignRole(RoleBasedAuthorizationStrategy.PROJECT , role.name, user)
            }
        }

    }



}
