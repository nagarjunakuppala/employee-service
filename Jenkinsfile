node  {
    def DOCKERHUB_REPO = "nagarjunakuppala07/employee-api"
    def DOCKER_SERVICE_ID = "employee-service"
    def DOCKER_IMAGE_VERSION = ""

    stage("clean workspace") {
        deleteDir()
    }

    stage("git checkout") {
        checkout scm

        def GIT_COMMIT = sh(returnStdout: true, script: "git rev-parse HEAD").trim().take(7)
        DOCKER_IMAGE_VERSION = "${BUILD_NUMBER}-${GIT_COMMIT}"
    }

    stage("mvn build") {
        sh "mvn clean install"
    }

    stage("docker build") {
        withDockerRegistry(credentialsId: 'dockerHub') {
    // some block
             sh "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} ."
        }
       
    }

    stage("docker push") {
        withDockerRegistry(credentialsId: 'dockerHub') {
            sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}"
        }
    }

    stage("docker service") {
        try {
            // Create the service if it doesn't exist otherwise just update the image
            sh """
                if [ \$(docker service ls --filter name=${DOCKER_SERVICE_ID} --quiet | wc -l) -eq 0 ]; then
                  docker service create \
                    --replicas 1 \
                    --name ${DOCKER_SERVICE_ID} \
                    --publish 9002:9002 \
                    --secret spring.datasource.url \
                    --secret spring.datasource.username \
                    --secret spring.datasource.password \
                    ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}
                else
                  docker service update \
                    --image ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} \
                    ${DOCKER_SERVICE_ID}
                fi
            """
        }
        catch (e) {
            sh "docker service update --rollback ${DOCKER_SERVICE_ID}"
            error "Service update failed. Rolling back ${DOCKER_SERVICE_ID}"
        }
        finally {
            sh "docker container prune -f"
            sh "docker image prune -af"
        }
    }
}
