pipeline {
    agent { label 'master' }
    options {
        buildDiscarder(logRotator(daysToKeepStr: '7' , numToKeepStr: '50', artifactDaysToKeepStr: '7', artifactNumToKeepStr: '7'))

        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
        quietPeriod(120)

    }
    stages {
        stage("Fetch and run the latest jobs") {
            steps {

                checkout([$class: 'GitSCM', branches: [[name: '*/master']],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [[$class:'SparseCheckoutPaths', sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:'src']] ]],
                          submoduleCfg: [],
                          userRemoteConfigs: [
                          [
//                              credentialsId: 'myCredentials',
                              url: 'https://github.com/lucasko-tw/jenkinsci']
                          ]
                        ])

                jobDsl  targets: 'src/jobs/*Jobs.groovy',
                        removedJobAction:    'DELETE',
                        removedViewAction:   'DELETE',
                        lookupStrategy:      'SEED_JOB',
                        additionalClasspath: 'src/main/groovy'
            }
        }

//        stage("Trigger seed jobs") {
//            agent { label 'seed' }
//            steps {
//                script {
//                    ["seed.Accounts","seed.createNodesAndUpdateLabels"].each { jobName ->
//                        jenkins.model.Jenkins.instance.queue.schedule(
//                                jenkins.model.Jenkins.instance.getJob(jobName),0)
//                    }
//                }
//            }
//        }

    }

//    post {
//        cleanup {
//            cleanWs()
//        }
//    }
}
