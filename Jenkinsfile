pipeline {
  agent any
  stages {
    stage('Mvn and Java version') {
      steps {
        sh '''mvn --version;        
java -version'''
      }
    }

    stage('Unit tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Create jar') {
      steps {
        sh 'mvn clean;mvn install ;mvn compile assembly:single;'
      }
    }
    stage ('deploy to Nexus') {
          steps {
              container ('maven') {
                  sh 'mvn deploy -DaltDeploymentRepository=nexus::default::http://nexus3:8081/repository/maven-nexus-repo/'
              }
          }
      }
    stage ('upload') {
        steps {
            archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
        }
    }
  }
}