// Lien vers Nexus, doit correspondre à l'instance paramétrée dans Jenkins
def nexusId = 'nexus_localhost'

/* *** Configuration de Nexus pour Maven ***/
// URL de Nexus
def nexusUrl = 'http://localhost:8081'
// Repo Id (provient du settings.xml nexus pour récupérer user/password)
def mavenRepoId = 'nexusLocal'

/* *** Repositories Nexus *** */
def nexusRepoSnapshot = "maven-snapshots"
def nexusRepoRelease = "maven-releases"



/* *** Détail du projet, récupéré dans le pipeline en lisant le pom.xml *** */
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

// Variable utilisée pour savoir si c'est une RELEASE ou une SNAPSHOT
def isSnapshot = true

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
              sh "mvn deploy:deploy-file -e -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${nexusUrl}/repository/${nexusRepoSnapshot} -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"
          }
      }
      stage('Push RELEASE to Nexus') {
          when { expression { !isSnapshot } }
          steps {
            nexusPublisher nexusInstanceId: 'nexus_localhost', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]]
          }
      }
  }
}