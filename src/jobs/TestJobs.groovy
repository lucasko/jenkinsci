job('test.job') {
    logRotator {
        numToKeep 20
    }
    parameters {
        stringParam('FOO_STRINGPARAM', '', 'string param example')
        booleanParam('FOO_BOOLEANPARAM', false, 'boolean param example')
        choiceParam('FOO_CHOICEPARAM', ['foo1', 'foo2','foo3'], 'choice param example')
    }
    steps {
        shell('echo Hello World!')
    }
    wrappers {
        //timestamps()
    }
}


pipelineJob('test.job.pipeline'){

    triggers {
        scm('H/5 * * * *')
    }

    definition{

        cps{
            script(readFileFromWorkspace('src/pipeline/TestJobs-pipeline.groovy'))
        }
    }
}