pipeline {
	environment {
		remoteServer = ''
		clientImage = "kargyris/client"
		registryCredential = 'dockerhub'
		dockerImage = ''
	}
    agent any
    stages {
        stage ('Git-checkout') {
            steps {
                echo "Checking out from git repository.";
            }
        }
        stage('Maven Build') {
            steps {
                sh 'mvn package';
            }
        }
		stage('Building Docker image') {
		  steps{
		    script {
		      dockerImage = docker.build clientImage
		    }
		  }
		}
		stage('Push Docker Image to Dockerhub') {
		  steps{
		     script {
		        docker.withRegistry( '', registryCredential ) {
		        dockerImage.push()
		      }
		    }
		  }
		}
		stage('Remove Unused Docker image') {
		  steps{
		    sh "docker rmi -f $clientImage"
		  }
		}
		stage('Deploy docker image from Dockerhub To Production Server') {
		  steps{
			script {
			        try {
            		    sh "sudo ssh -o StrictHostKeyChecking=no -oIdentityFile=/home/ubuntu/.ssh/FinalJenkins2.pem ubuntu@$remoteServer \'sudo docker stop \$(sudo docker ps -a -q) || true && sudo docker rm \$(sudo docker ps -a -q) || true\'"

			        }
			        catch (exc) {
			            echo "Error while removing images, continue..., cause: " + exc;
			        }
			    }

		    sh "sudo ssh -o StrictHostKeyChecking=no -oIdentityFile=/home/ubuntu/.ssh/FinalJenkins2.pem ubuntu@$remoteServer \'sudo docker run -d -p 20001:20001 $clientImage\'"
		  }
		}
		stage('Copy client.sh to Production Server') {
		  steps{
		    sh "scp -i /home/ubuntu/.ssh/FinalJenkins2.pem client.sh ubuntu@$remoteServer:/home/ubuntu"
		  }
		}
    }
    post {
        always {
            echo "Always execute this."
        }
        success  {
            echo "Execute when run is successful."
        }
        failure  {
            echo "Execute run results in failure."
        }
        unstable {
            echo "Execute when run was marked as unstable."
        }
        changed {
            echo "Execute when state of pipeline changed (failed <-> successful)."
        }
    }
}