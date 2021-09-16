#Start with a base image containing Java runtime
FROM openjdk:latest

VOLUME /main-app

#Add the application's jar to the container
COPY target/loans-0.0.1-SNAPSHOT.jar loans.jar

EXPOSE 6060

#Execute the application
ENTRYPOINT ["java","-jar","loans.jar"] 