FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

WORKDIR /build

COPY src ./src

COPY pom.xml .

RUN mvn clean install -DskipTests

FROM alpine:latest AS run

LABEL authors="CapyLover"

ARG APP_VERSION

WORKDIR /app

COPY --from=build build/target/Sweater-${APP_VERSION}.jar ./sweater-app.jar

RUN apk update

RUN apk add openjdk21-jre

EXPOSE 8080

CMD ["java", "-jar", "sweater-app.jar"]