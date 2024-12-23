pipeline {
    agent any

    environment {
        // Définissez les variables d'environnement pour SonarCloud
        SONAR_HOST_URL = 'https://sonarcloud.io'
        SONAR_LOGIN = credentials('sonarcloud-token')  // Nom du credential stocké dans Jenkins (Token SonarCloud)
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source depuis le dépôt Git
                git branch: 'main', url: 'https://github.com/salmaimassadan/Gestion_de_reservation_restaurants_sonarqube.git'
            }
        }

        stage('Build') {
            steps {
                // Compiler le projet Maven
                script {
                    // Exécuter Maven Clean, Install et l'analyse SonarCloud
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                // Lancer l'analyse avec SonarCloud
                script {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=mgmnt-qualite_jee \
                        -Dsonar.organization=mgmnt-qualite \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_LOGIN}
                    """
                }
            }
        }

        stage('Post-build Actions') {
            steps {
                // Exemple d'actions post-build (notifications, etc.)
                echo 'Analyse terminée avec SonarCloud'
            }
        }
    }

    post {
        always {
            // Actions toujours exécutées après le pipeline (même en cas d'échec)
            echo 'Pipeline terminé'
        }
        success {
            // Actions en cas de succès
            echo 'Pipeline exécuté avec succès'
        }
        failure {
            // Actions en cas d'échec
            echo 'Le pipeline a échoué'
        }
    }
}
