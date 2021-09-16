#Start with a base image containing Java runtime
FROM openjdk:latest

#Add the application's jar to the container
COPY target/configserver-0.0.1-SNAPSHOT.jar configserver.jar

EXPOSE 8071

#Execute the application
ENTRYPOINT ["java","-jar","configserver.jar"]
