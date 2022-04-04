FROM maven:3-openjdk-17-slim as build

WORKDIR /usr/src/app

COPY . .

RUN mvn dependency:go-offline

RUN mvn clean package

FROM openjdk:17-jdk-alpine

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/launcher/target/*.jar /home/spring/app.jar

RUN addgroup -S spring \
    && adduser -S spring -G spring

USER spring:spring

WORKDIR /home/spring

CMD java -jar ./app.jar
