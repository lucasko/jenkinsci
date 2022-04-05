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
}


 
