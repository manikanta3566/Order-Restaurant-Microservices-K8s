# Stage 1: Build the Spring Boot application
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Set working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app will run on
EXPOSE 9090

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
