import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.TriggersConfig;

 class  MyPlugins {
  static void main(String... args) {

   MyPlugins myPlugins = new MyPlugins();
   myPlugins.setupSonar();
  }

  void setupSonar(){
   def jenkins_instance = Jenkins.getInstance()
   def sonar_global_conf = jenkins_instance.getDescriptor(SonarGlobalConfiguration.class)
   def sonar_installations = sonar_global_conf.getInstallations()

   def sonar_name = "MySonar"
   def sonar_server_url = "http://192.168.1.188:9000"
   def sonar_credential_id = "my-sonar-id"
//   def sonar_auth_token = "my-sonar-token"
   def sonar_webhook_id = ''
   def sonar_mojo_version = ''
   def sonar_additional_properties = ''
   def sonar_additional_analysis_properties = ''
   def sonar_triggers = new TriggersConfig()

    def new_inst = new hudson.plugins.sonar.SonarInstallation(
           sonar_name,
           sonar_server_url,
           sonar_credential_id,
           null, // Secret serverAuthenticationToken
           sonar_webhook_id,
           sonar_mojo_version,
           sonar_additional_properties,
           sonar_additional_analysis_properties,
           sonar_triggers
   )
   sonar_installations += new_inst
   println("add new sonar installation success :" + sonar_name);

   sonar_global_conf.setInstallations((SonarInstallation[]) sonar_installations)
   sonar_global_conf.save()
   jenkins_instance.save()

  }
 }