// import jenkins.model.*
// import hudson.model.*
// import hudson.slaves.*
// import hudson.plugins.sshslaves.*
// import java.util.ArrayList;
// import hudson.slaves.EnvironmentVariablesNodeProperty.Entry;
// import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy
//
//   List<Entry> env = new ArrayList<Entry>();
//   env.add(new Entry("key1","value1"))
//   env.add(new Entry("key2","value2"))
//   EnvironmentVariablesNodeProperty envPro = new EnvironmentVariablesNodeProperty(env);
//   Slave slave = new DumbSlave(
//                     "my-node",
//                      "This Node with SSH",
//                     "/home/centos/_jenkins5566",
//                     "1",
//                     Node.Mode.NORMAL,
//                     "my-centos",
//                     new SSHLauncher(
//                             "192.168.228.148",
//                             22,
//                             SSHLauncher.lookupSystemCredentials("my_id"),
//                             "",   // jvmOptions
//                             null, //javaPath
//                             null, //prefixStartSlaveCmd
//                             "",   //suffixStartSlaveCmd
//                             60,
//                             3,
//                             15,
//                             new NonVerifyingKeyVerificationStrategy()
//                     ),
//                     new RetentionStrategy.Always()
//                     )
//   slave.getNodeProperties().add(envPro)
//   Jenkins.instance.addNode(slave)