 import com.cloudbees.plugins.credentials.impl.*;
 import com.cloudbees.plugins.credentials.*;
 import com.cloudbees.plugins.credentials.domains.*;
 import org.jenkinsci.plugins.plaincredentials.*
 import org.jenkinsci.plugins.plaincredentials.impl.*
 import com.cloudbees.plugins.credentials.common.*
 import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
 import hudson.util.Secret

 class  Credential {
  static void main(String... args) {

   // 1. Add basic credential
   Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(
           CredentialsScope.GLOBAL, // Scope
           "my_id", // id
           "My description", // description
           "centos", // username
           "centos" // password
   );

   SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), c);

// 2. Add Secret Text
   StringCredentialsImpl credentials = new StringCredentialsImpl(
           GLOBAL,
           "my-sonar-id",
           "Secret Text for something",
           Secret.fromString("S3cr3t"))

   SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), credentials);

  }