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
                  sh 'mvn deploy -DaltDeploymentRepository=nexus::default::http://localhost:8081/repository/maven-snapshots/'
              }
          }
      }
    stage ('upload') {
        steps {
            archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
        }
    }

    stage('Deploy') {
      steps {
        script {
        pom = readMavenPom file: "pom.xml";
        filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
        artifactPath = filesByGlob[0].path;
        artifactExists = fileExists artifactPath;
        if(artifactExists) {
            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
            nexusArtifactUploader(
                nexusVersion: "nexus3",
                protocol: "HTTP",
                nexusUrl: "http://localhost:8081",
                groupId: pom.groupId,
                version: pom.version,
                repository: "http://localhost:8081/repository/maven-nexus-repo/",
                credentialsId: "jenkins",
                artifacts: [
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: artifactPath,
                    type: pom.packaging],
                    [artifactId: pom.artifactId,
                    classifier: '',
                    file: "pom.xml",
                    type: "pom"]
                ]
            );
        } else {
            error "*** File: ${artifactPath}, could not be found";
        }
      }
    }
  }
}
}