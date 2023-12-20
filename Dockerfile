FROM openjdk:17-jdk-alpine

ADD target/smartwateringV2*.jar /app.jar

CMD [ "java","-jar","/app.jar","--spring.profiles.active=prod" ]

EXPOSE 8080