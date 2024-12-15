# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/productcatalog-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
