#Start with a base image containing Java runtime
FROM openjdk:latest

#Add the application's jar to the container
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver.jar

EXPOSE 8070

#Execute the application
ENTRYPOINT ["java","-jar","eurekaserver.jar"] 