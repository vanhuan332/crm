# syntax=docker/dockerfile:1
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml ./
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app
RUN addgroup -S crm && adduser -S crm -G crm
COPY --from=build /workspace/target/ai-native-crm-*.jar app.jar
USER crm
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

