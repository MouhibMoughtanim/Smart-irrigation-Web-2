pipeline {
    agent any

    tools {
        maven 'maven-3.9.6'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // This automatically uses the Maven installation configured in the 'tools' section
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
}
}
}