
pipeline {
    agent any

    environment {
        DELIVERY_SERVICE = 'Служба доставки'
        JAVA_HOME = '/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home'
        PATH = "/opt/homebrew/bin:/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home/bin:${env.PATH}"
    }

    stages {

        stage('Build') {
            steps {
                echo 'Этап 1: Компиляция проекта'
                echo "Название проекта: ${env.DELIVERY_SERVICE}"
                sh 'java -version'
                sh 'mvn -v'
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Этап 2: Запуск тестов'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Этап 3: Упаковка проекта'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Pipeline выполнен успешно!'
        }
        failure {
            echo 'При выполнении Pipeline произошла ошибка!'
        }
    }
}
