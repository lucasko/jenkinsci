import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.scm.SvnCheckoutStrategy
import javaposse.jobdsl.dsl.helpers.scm.SvnDepth


//
//job('seed.seed') {
//    description("seed job")
//  //  label('seed')
////    disabled(true)
//    logRotator {
//        numToKeep 20
//    }
//    quietPeriod(5)
//
//    steps {
//
//        shell("""
//            cp -r  \$JENKINS_HOME/src ./
//            """.stripIndent())
//        dsl{
//            external('src/jobs/*Jobs.groovy')
//        }
//        systemGroovyCommand("""
//        import org.jenkinsci.plugins.scriptsecurity.scripts.*
//        ScriptApproval scriptApproval = ScriptApproval.get()
//        scriptApproval.pendingScripts.each {
//            scriptApproval.approveScript(it.hash)
//        }
//        println "Done"
//        """.stripIndent())
//    }
//    triggers {
//        scm('* * * * *')
//    }
//}


pipelineJob('seed.seed') {
    triggers {
        scm('* * * * *')
    }
    environmentVariables {
    }
    definition {
        cps {
            script(readFileFromWorkspace('src/pipeline/seed-pipeline.groovy'))
        }
    }
}


job('seed.Accouns') {
    description("accouns job")
    //  label('seed')
//    disabled(true)
    logRotator {
        numToKeep 20
    }
    quietPeriod(5)

    steps {
        systemGroovyCommand(readFileFromWorkspace("src/init.groovy.d/Accounts.groovy")) {}
    }
//    triggers {
//        scm('* * * * *')
//    }
}

