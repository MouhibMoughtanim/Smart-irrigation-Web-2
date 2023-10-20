FROM openjdk:8-jdk-alpine
VOLUME [ "C:\Users\dell\Desktop\S5" ]
ADD target/smartwateringV2*.jar /app.jar
CMD [ "java","-jar","/app.jar","--spring.profiles.active=prod" ]
EXPOSE 8080