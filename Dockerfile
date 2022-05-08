FROM maven:3-openjdk-17-slim as build

WORKDIR /usr/src/app

COPY . .

RUN mvn dependency:go-offline

RUN mvn clean package

FROM openjdk:17-jdk

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/*.jar /home/spring/app.jar

WORKDIR /home/spring

CMD java -jar ./app.jar
