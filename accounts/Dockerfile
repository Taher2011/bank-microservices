#Start with a base image containing Java runtime
FROM openjdk:latest

VOLUME /main-app

#Add the application's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts.jar

EXPOSE 5050

#Execute the application
ENTRYPOINT ["java","-jar","accounts.jar"]
