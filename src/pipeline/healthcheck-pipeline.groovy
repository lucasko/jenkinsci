
def map = [
        [
                name:"google",
                url:"http://www.google.com"
        ],
        [
                name:"ntu",
                url:"http://www.ntust.edu.tw"
        ],
        [
                name:"ntust",
                url:"http://www.ntust.edu.tw"
        ]
        ]

pipeline {
    agent { label 'built-in' }

    stages {
        stage('Health Check') {
            steps {
                script {
                    def jobs = [:]
                    map.each{

                        jobs[it.name] = {
                            stage(it.name) {
                                echo "checking ${it.url} ..."
                            }
                        }
                    }
                    parallel jobs
                }
            }
        }

        stage('Run Health Check') {
            parallel {
                //failFast true  // force stop all branches of parallel if one failed.

                stage('google') {
                    steps {
                        script{
                           healthcheck("http://www.google.com")
                        }
                    }

                }
                stage('ntust') {

                    steps {
                       script{
                           healthcheck("http://www.ntust.edu.tw")
                       }
                    }

                }
            }
        }












////////////// MATRIX


//        stage('Run HealCheck2') {
//            matrix {
//                axes {
//                    axis {
//                        name 'checkURL'
//                        values 'http://www.ntu.edu.tw', 'http://www.pttweb.cc/', 'http://yahoo.com.tw/'
//                    }
//                    axis {
//                        name 'BROWSER'
//                        values 'chrome', 'firefox'
//                    }
//                }
//                stages {
//                    //agent any
//                    stage("check $checkURL") {
//                        steps {
//                            echo "checking ${checkURL} by ${BROWSER} ..."
//                            healthcheck("$checkURL")
//                        }
//                    }
//                }
//            }
//        }


    }
}

def healthcheck(url)
{
    def command = "curl -Is $url | head -n 1"
    def result = sh(script: command, returnStdout: true)

    print result

    if (!result.contains("200"))
        unstable('check failed!')
}