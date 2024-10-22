FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/telefoni-backend-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]