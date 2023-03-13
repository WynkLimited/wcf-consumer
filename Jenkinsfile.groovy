node('master') {
    env.JAVA_HOME = "/opt/jdk-14.0.2"
    env.docker_registry = '536123028970.dkr.ecr.ap-south-1.amazonaws.com'
    env.imagename = "wynk-music-wcf-conusmer"
    env.region = "ap-south-1"
    def service_name = "wynk-music-wcf-conusmer"
    stage('GitClone') {
        git branch: "$branch_name", credentialsId: "git", url: 'git@github.com:WynkLimited/wcf-consumer.git'
    }
    stage('build') {
        sh """
        cd catalog-artist
        mvn clean package -DskipTests
        docker build -t "$docker_registry"/"$imagename":${JOB_NAME}-${BUILD_NUMBER} .
        """
    }
    stage('push image to ECR') {
        sh """
        /usr/local/bin/aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${docker_registry}
        docker push "$docker_registry"/"$imagename":${JOB_NAME}-${BUILD_NUMBER}
        """
    }
    stage('updateOnCluster') {
        sh """
        export PATH=/usr/local/bin:$PATH
        which kubectl
        aws eks --region "$region" update-kubeconfig --name "$cluster_name"
        kubectl get svc -n staging
        kubectl set image deployment/"$service_name" $service_name="$docker_registry"/"$imagename":${JOB_NAME}-${BUILD_NUMBER} -n staging
        """
    }
    stage('clean up') {
        sh """
        docker image rm "$docker_registry"/"$imagename":${JOB_NAME}-${BUILD_NUMBER}
        """
    }
}
