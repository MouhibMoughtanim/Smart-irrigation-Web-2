pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17-alpine' 
            args '-v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock --network host'

        }
    }
    stages {
        stage('Debug') { 
            steps {
                sh 'ping google.com' 
                sh 'mvn -X help:effective-settings'
                sh 'mvn -X help:active-profiles'
                sh 'mvn -X help:active-profiles -Dall-profiles'
                sh 'mvn -X help:active-profiles -Dall-profiles -Ddetail'
                sh 'mvn -X help:active-profiles -Dall-profiles -Ddetail -Dverbose'
            }
        }
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}
