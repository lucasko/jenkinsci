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

   def sonar_name = "New setting"
   def sonar_server_url = "http://192.168.1.188:9000"
   def sonar_auth_token = "d72bf1c637cd9b95899192a97b33454b94a8da14"
   def sonar_mojo_version = ''
   def sonar_additional_properties = ''
   def sonar_triggers = new TriggersConfig()
   def sonar_additional_analysis_properties = ''

   def new_inst = new SonarInstallation(
           sonar_name,
           sonar_server_url,
           sonar_auth_token,
           sonar_mojo_version,
           sonar_additional_properties,
           sonar_triggers,
           sonar_additional_analysis_properties
   )
   sonar_installations += new_inst
   println("add new  sonar installation success :" + sonar_name);

   sonar_global_conf.setInstallations((SonarInstallation[]) sonar_installations)
   sonar_global_conf.save()
   jenkins_instance.save()


  }
 }