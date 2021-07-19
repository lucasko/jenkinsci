environment{

}

pipeline {
    agent any 

    stages {

        stage("Git") {
            steps {
                git url:  "https://github.com/lucasko-tw/gradle-task-example.git" 
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

    }//end of stages
}


 
