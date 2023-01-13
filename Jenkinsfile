def nexusId = 'nexus_localhost'
def nexusUrl = 'http://nexus:8081'
def mavenRepoId = 'nexusLocal'
def nexusRepoSnapshot = "maven-snapshots"
def nexusRepoRelease = "maven-releases"
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

def isSnapshot = true

pipeline {
  agent any
  stages {
    stage('Mvn and Java version') {
      steps {
        sh 'mvn --version;java -version'
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
    stage('Get info from POM') {
          steps {
            script {
                pom = readMavenPom file: 'pom.xml'
                groupId = pom.groupId
                artifactId = pom.artifactId
                packaging = pom.packaging
                version = pom.version
                filepath = "target/${artifactId}-${version}.jar"
                isSnapshot = version.endsWith("-SNAPSHOT")
            }
            echo groupId
            echo artifactId
            echo packaging
            echo version
            echo filepath
            echo "isSnapshot: ${isSnapshot}"
          }
      }
    
    stage('Build') {
        steps {
            sh 'mvn clean package'
        }
    }
    stage('Push SNAPSHOT to Nexus') {
      when { expression { isSnapshot } }
      steps {
        sh "mvn deploy:deploy-file -e -Dinternal.repo.username=admin -Dinternal.repo.password=admin -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${nexusUrl}/repository/${nexusRepoSnapshot}/ -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"
      }
    }

    stage('Push RELEASE to Nexus') {
      when { expression { !isSnapshot }}
      steps {
        nexusPublisher(nexusInstanceId: 'nexus_localhost', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]])
      }
    }
stage('SonarQube') {
      steps {
        sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=sonar_test -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_b0f241d10c6997fab010bd36634a57a2c4935b2a'
      }
    }
  }
}
