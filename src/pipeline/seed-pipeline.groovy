pipeline {
    agent { label 'seed' }
    options {
        buildDiscarder(logRotator(daysToKeepStr: '7' , numToKeepStr: '50', artifactDaysToKeepStr: '7', artifactNumToKeepStr: '7'))

        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
        timestamps()
        quietPeriod(120)

    }
    stages {
        stage("Fetch and run the latest jobs") {
            steps {
                checkoutGit(
                        url:        GIT_URL,
                        branchName: GIT_BRANCH,
                        extensions: [[$class:'SparseCheckoutPaths', sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:'src']] ]]
                )
                jobDsl  targets: SEED_PATTERN,
                        removedJobAction:    'DELETE',
                        removedViewAction:   'DELETE',
                        lookupStrategy:      'SEED_JOB',
                        additionalClasspath: 'src/main/groovy'
            }
        }

        stage("Trigger seed jobs") {
            agent { label 'seed' }
            steps {
                script {
                    ["seed.Accounts","seed.createNodesAndUpdateLabels"].each { jobName ->
                        jenkins.model.Jenkins.instance.queue.schedule(
                                jenkins.model.Jenkins.instance.getJob(jobName),0)
                    }
                }
            }
        }

    }

    post {
        cleanup {
            cleanWs()
        }
    }
}
