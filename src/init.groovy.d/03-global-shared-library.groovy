import jenkins.model.Jenkins
import jenkins.plugins.git.GitSCMSource
import jenkins.plugins.git.traits.BranchDiscoveryTrait
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever

List libraries = [] as ArrayList

// def remote = System.getenv("PIPELINE_SHARED_LIB_REMOTE")
def credentialsId = System.getenv("PIPELINE_SHARED_LIB_CREDS_ID")

name = 'pipeline-lib'
defaultVersion = 'master'

someId = null
credentialId = null

repo = "https://github.com/lucasko/jenkins-shared-library"

// if (remote != null) {

	def scm = (new GitSCMSource(
	    someId,
	    repo,
	    credentialId, // Credentials
	    "*",
	    "",
	    false))


    // def scm = new GitSCMSource(remote)
    // if (credentialsId != null) {
    //     scm.credentialsId = credentialsId
    // }

    scm.traits = [new BranchDiscoveryTrait()]
    def retriever = new SCMSourceRetriever(scm)

    def library = new LibraryConfiguration(name, retriever)
    library.defaultVersion = defaultVersion
    library.implicit = false
    library.allowVersionOverride = true
    library.includeInChangesets = true

    libraries << library

    def global_settings = Jenkins.instance.getExtensionList(GlobalLibraries.class)[0]
    global_settings.libraries = libraries
    global_settings.save()
    println 'Configured Pipeline Global Shared Libraries:\n    ' + global_settings.libraries.collect { it.name }.join('\n    ')
// }