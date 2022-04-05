import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.TriggersConfig;
import hudson.tools.*

 class  MyPlugins {
  static void main(String... args) {

   MyPlugins myPlugins = new MyPlugins();
   myPlugins.setupSonar();
  }

  void setupSonar(){


   // Step 1
   def jenkins_instance = Jenkins.getInstance()
   def sonar_global_conf = jenkins_instance.getDescriptor(SonarGlobalConfiguration.class)
   def sonar_installations = sonar_global_conf.getInstallations()

   def sonar_name = "MySonar"
   def sonar_server_url = "http://192.168.1.188:9000"
   def sonar_credential_id = "my-sonar-id"
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


   def sonar_runner_name = "my-sonar-scanner"
   def sonar_runner_version = "4.7.0.2747"
   // Step 2 - Configure SonarRunner
   println("Configuring SonarRunner...")
   def desc_SonarRunnerInst = jenkins_instance.getDescriptor("hudson.plugins.sonar.SonarRunnerInstallation")
   def sonarRunnerInstaller = new SonarRunnerInstaller(sonar_runner_version)
   def installSourceProperty = new InstallSourceProperty([sonarRunnerInstaller])
   def sonarRunner_inst = new SonarRunnerInstallation(sonar_runner_name + sonar_runner_version, "", [installSourceProperty])

   def sonar_runner_installations = desc_SonarRunnerInst.getInstallations()
   def sonar_runner_inst_exists = false
   sonar_runner_installations.each {
    installation = (SonarRunnerInstallation) it
    if (sonarRunner_inst.getName() == installation.getName()) {
     sonar_runner_inst_exists = true
     println("Found existing installation: " + installation.getName())
    }
   }
   if (!sonar_runner_inst_exists) {
    sonar_runner_installations += sonarRunner_inst
    desc_SonarRunnerInst.setInstallations((SonarRunnerInstallation[]) sonar_runner_installations)
    desc_SonarRunnerInst.save()
   }

   jenkins_instance.save()

  }
 }