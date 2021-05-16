FROM openjdk:8
COPY ./smartchoice-api/target/*.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/tmp/smartchoice-0.0.1-SNAPSHOT.jar"]
