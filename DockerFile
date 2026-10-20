# Build the Kotlin application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY . .
RUN mvn -B -DskipTests package

# Runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /build/target/AndyProxyServer-1.0-SNAPSHOT.jar /app/app.jar
COPY --from=build /build/native/ /app/native/

RUN chmod +x /app/native/server

CMD ["java", "-jar", "/app/app.jar"]