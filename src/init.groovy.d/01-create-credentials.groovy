 import com.cloudbees.plugins.credentials.impl.*;
 import com.cloudbees.plugins.credentials.*;
 import com.cloudbees.plugins.credentials.domains.*;
 import org.jenkinsci.plugins.plaincredentials.*
 import org.jenkinsci.plugins.plaincredentials.impl.*
 import com.cloudbees.plugins.credentials.common.*
 import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
 import static com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL
 import com.cloudbees.plugins.credentials.domains.Domain
 import com.cloudbees.plugins.credentials.SystemCredentialsProvider
 import hudson.util.Secret
 import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl

 class  MyCredential {
  static void main(String... args) {

   // 1. Add basic credential
   Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(
           GLOBAL, // Scope
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
           "my-sonar-token",
           Secret.fromString("d72bf1c637cd9b95899192a97b33454b94a8da14")
   )

   SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), credentials);

  }
 }