import jenkins.model.Jenkins
import groovy.json.JsonSlurper


class  MySMTP {
    static void main(String... args) {

        def instance = Jenkins.getInstance()
        def emailExt = instance.getDescriptor("hudson.plugins.emailext.ExtendedEmailPublisher")


//def envVars = Jenkins.instance.getGlobalNodeProperties()[0].getEnvVars()
        def jsonSlurper = new JsonSlurper()

        def props = jsonSlurper.parse(new File('/code/secret.json'))
        print props

        def smtpUser = props['SMPT_USER']
        def smtpPassword = props['SMPT_PASSWORD']
        def replyTo = props['REPLY_TO']
        def smtpServer = props['SMPT_SERVER']
        def smtpPort = props['SMPT_PORT']

        emailExt.setSmtpAuth(smtpUser, smtpPassword)
        emailExt.setDefaultReplyTo(replyTo)
        emailExt.setSmtpServer(smtpServer)
//        emailExt.setUseSsl(true)
        emailExt.setSmtpPort(smtpPort)
        emailExt.setCharset("utf-8")
//emailExt.setDefaultRecipients("someone@example.com")

        emailExt.save()

    }
}