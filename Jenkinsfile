pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'build done1'
        sh './gradlew clean build'
      }
    }

    stage('upload') {
      steps {
        echo 'upload done2'
        sh 'aws s3 cp build/libs/application.war s3://backbkgoal/application.war --region us-east-1'
      }
    }

    stage('deploy') {
      steps {
        echo 'deploy done3'
        sh 'aws elasticbeanstalk create-application-version --region us-east-1 --application-name springboot-goal --version-label ${BUILD_TAG} --source-bundle S3Bucket="backbkgoal",S3Key="application.war"' 
        sh 'aws elasticbeanstalk update-environment --region us-east-1 --environment-name Springbootgoal-env --version-label ${BUILD_TAG}' 
        slackSend (color: '#00cec9', message: "Backend 빌드 완료. 성공 실패는 따로 확인!!! STATUS : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
      }
    }
  }
}