FROM maven:3-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean test package

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar /app.jar

CMD java -jar ./app.jar
