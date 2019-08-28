// import jenkins.model.*
// import hudson.model.*
// import hudson.slaves.*
// import hudson.plugins.sshslaves.*
// import java.util.ArrayList;
// import hudson.slaves.EnvironmentVariablesNodeProperty.Entry;
// import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy

//   List<Entry> env = new ArrayList<Entry>();
//   env.add(new Entry("key1","value1"))
//   env.add(new Entry("key2","value2"))
//   EnvironmentVariablesNodeProperty envPro = new EnvironmentVariablesNodeProperty(env);
//   Slave slave = new DumbSlave(
//                     "my-node","This Node with SSH",
//                     "/jenkins",
//                     "1",
//                     Node.Mode.NORMAL,
//                     "",
//                     new SSHLauncher(
//                             "192.168.1.1",
//                             22,
//                             SSHLauncher.lookupSystemCredentials("my-id"),
//                             "",
//                             null,
//                             null,
//                             "",
//                             "",
//                             60,
//                             3,
//                             15,
//                             new NonVerifyingKeyVerificationStrategy()
//                     ),
//                     new RetentionStrategy.Always(),
//                     new LinkedList())
//   slave.getNodeProperties().add(envPro)
//   Jenkins.instance.addNode(slave)