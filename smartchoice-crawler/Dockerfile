FROM openjdk:8
COPY ./target/*.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/tmp/smartchoice-crawler-0.0.1-SNAPSHOT.jar"]
