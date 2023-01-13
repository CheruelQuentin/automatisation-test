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

      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('SonarQube') {
      steps {
        sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=sonar_test -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_f547db3f7da9925a15ce6dea1eaf96eceb3a366e'
      }
    }

    stage('Push SNAPSHOT to Nexus') {
      when {
        expression {
          isSnapshot
        }

      }
      steps {
        sh "mvn deploy:deploy-file -e -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Dusername='admin' -Dpwd='admin' -Durl=${nexusUrl}/repository/${nexusRepoSnapshot}/ -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"
      }
    }

    stage('Push RELEASE to Nexus') {
      when {
        expression {
          !isSnapshot
        }

      }
      steps {
        nexusPublisher(nexusInstanceId: 'nexus_localhost', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]])
      }
    }

  }
}
