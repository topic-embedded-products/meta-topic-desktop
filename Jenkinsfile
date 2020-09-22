#!/bin/env groovy
// This is a Jenkins pipeline file. It will be picked up by Jenkins and contains
// instructions this project should be built and tested.

pipeline {
    agent {
        node {
            label 'openembedded'
            customWorkspace 'topic-desktop-zeus'
        }
    }

    stages {
        stage('Build') {
            steps {
                checkout scm
                sh 'scripts/autobuild.sh'
            }
        }

        stage('Publish artifacts') {
            steps {
                archiveArtifacts artifacts: 'build/artefacts/*', onlyIfSuccessful: true
            }
        }

        stage('Push github') {
              when { branch 'master' } // Only run this step on the master branch
              steps {
                sh 'git push git@github.com:topic-embedded-products/meta-topic-desktop.git HEAD:refs/heads/master'
            }
        }

        stage('Update downloads') {
            when { branch 'release' }
            steps {
                sh 'scripts/release-downloads.sh'
            }
        }
    }
}
