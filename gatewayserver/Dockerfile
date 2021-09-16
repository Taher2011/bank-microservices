#Start with a base image containing Java runtime
FROM openjdk:latest

#Add the application's jar to the container
COPY target/gatewayserver-0.0.1-SNAPSHOT.jar gatewayserver.jar

EXPOSE 8073

#Execute the application
ENTRYPOINT ["java","-jar","gatewayserver.jar"] 