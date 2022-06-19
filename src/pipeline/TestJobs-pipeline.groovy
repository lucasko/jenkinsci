import hudson.plugins.sonar.SonarBuildWrapper


pipeline {
    agent any

    stages {

        stage("Git") {
            steps {
                git url:  "https://github.com/lucasko/gradle-task-example.git"
            }
        }

        stage('Build') {
            steps{
                sh './gradlew build --profile --no-daemon -s'
            }
        }

        stage('Test') {
            steps{
                sh 'echo "./gradlew  test"'
            }
        }


        stage('Hadolint Test') {

            environment{
                HADOLINT_HOME = '/usr/bin/hadolint' // docker run --rm -i hadolint/hadolint  < Dockerfile
            }

            steps{
                sh "cat Dockerfile"
                script {
                    sh "cat hadolint-config.yaml"
                    sh "$HADOLINT_HOME --config hadolint-config.yaml Dockerfile"
                }
            }
        }

        stage('Build Docker Image') {
            steps{
                sh "cat Dockerfile"
                sh "cat Makefile"
                sh "make build_image"
            }
        }

        stage("sonar"){
            steps{
                script{
                    sh "env"
                    sh "java -version"
                    def scannerHome = tool 'my-sonar-scanner';
                    withSonarQubeEnv( envOnly: true) {
                        println "${scannerHome}/bin/sonar-scanner"
                        println "${env.SONAR_HOST_URL} "
                        println "${env.SONAR_AUTH_TOKEN}"
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=test-project"
                    }
                }
            }
        }
    }//end of stages

//    post{
//        always{
//            emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'lucasko.tw@gmail.com'
//        }
//    }

}


 
