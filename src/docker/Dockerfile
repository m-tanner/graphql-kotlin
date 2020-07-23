FROM openjdk:8-jdk-alpine

# assuming a build command like this (these paths)
# docker build -t kotlin-test -f target/docker/Dockerfile target/docker
ADD hello-kotlin-service.jar hello-kotlin-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "hello-kotlin-service.jar"]