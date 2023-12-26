pipeline {
    agent any

    tools {
        maven 'maven-3.9.6'
        docker 'docker' // 'docker' should match the Docker installation name in your Jenkins configuration
    }

    stages {
        stage('Build') {
            steps {
                script {
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

        stage('Docker Publish') {
            steps {
                script {
                    // Build and tag the Docker image
                    sh 'docker build -t your-docker-username/your-image-name:latest .'

                    // Authenticate with Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                    }

                    // Push the Docker image to Docker Hub
                    sh 'docker push your-docker-username/your-image-name:latest'
                }
            }
}
}
}