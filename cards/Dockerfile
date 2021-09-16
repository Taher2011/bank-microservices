#Start with a base image containing Java runtime
FROM openjdk:latest

VOLUME /main-app

#Add the application's jar to the container
COPY target/cards-0.0.1-SNAPSHOT.jar cards.jar

EXPOSE 7070

#Execute the application
ENTRYPOINT ["java","-jar","cards.jar"]