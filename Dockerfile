FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

WORKDIR /app

COPY . .

RUN ./mvnw install -DskipTests

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/gestor-pedidos-telu-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
