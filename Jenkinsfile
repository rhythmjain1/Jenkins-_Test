pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        COMPOSE_FILE = 'docker-compose.yml'
        COMPOSE_PROJECT_NAME = 'rhythm_jenkins_test'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Preflight') {
            steps {
                sh 'java -version'
                sh 'docker --version'
                sh 'docker compose version || docker-compose --version'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw clean test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw -DskipTests package'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    if docker compose version >/dev/null 2>&1; then
                        COMPOSE_CMD="docker compose"
                    else
                        COMPOSE_CMD="docker-compose"
                    fi

                    $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} down --remove-orphans || true
                    $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} build --pull
                    $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} up -d --remove-orphans
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    if docker compose version >/dev/null 2>&1; then
                        COMPOSE_CMD="docker compose"
                    else
                        COMPOSE_CMD="docker-compose"
                    fi

                    $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} ps
                    docker ps
                '''
            }
        }
    }

    post {
        failure {
            sh '''
                if docker compose version >/dev/null 2>&1; then
                    COMPOSE_CMD="docker compose"
                else
                    COMPOSE_CMD="docker-compose"
                fi

                $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} ps || true
                $COMPOSE_CMD -p ${COMPOSE_PROJECT_NAME} -f ${COMPOSE_FILE} logs --tail=100 || true
            '''
        }
    }
}
