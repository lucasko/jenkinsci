import hudson.plugins.sonar.SonarBuildWrapper
environment{

}

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


        stage('Dockerfile Test') {
            steps{
                //sh 'docker run --rm -i hadolint/hadolint  < Dockerfile'
                sh "cat Dockerfile"
                sh 'hadolint < Dockerfile'
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

    post{
        always{
            emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'lucasko.tw@gmail.com'
        }
    }

}


 
