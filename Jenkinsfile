pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-Storage'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    error 'The tests failed'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker stop LPDM-StorageMS || true && docker rm LPDM-StorageMS || true'
                sh 'docker pull vyjorg/lpdm-storage:latest'
                sh 'docker run -d --name LPDM-OrderMS -p 28083:28083 --link LPDM-StorageDB --restart always --memory-swappiness=0  vyjorg/lpdm-storage:latest'
            }
        }
    }
}