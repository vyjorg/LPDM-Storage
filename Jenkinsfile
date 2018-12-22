pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    environment {
        KEY = ''
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-Storage'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn test'
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
        stage('Load Key') {
            steps {
                script {
                    configFileProvider([configFile(fileId: '2bd4e734-a03f-4fce-9015-aca988614b4e', targetLocation: 'lpdm.key')]) {
                        lpdm_keys = readJSON file: 'lpdm.key'
                        KEY = lpdm_keys.storage
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                sh "docker stop LPDM-StorageMS || true && docker rm LPDM-StorageMS || true"
                sh "docker pull vyjorg/lpdm-storage:latest"
                sh "docker run -d --name LPDM-StorageMS -p 28089:28089 --link LPDM-StorageDB -v /var/www/lpdm/storage/files:/files:consistent --restart always --memory-swappiness=0  -e 'JAVA_TOOL_OPTIONS=-Djasypt.encryptor.password=$KEY' vyjorg/lpdm-storage:latest"
            }
        }
    }
}
