# Stage 1: Build the JAR file
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy the project source code
COPY . .

# Build the Spring Boot application (creates target/*.jar)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using OpenJDK 21
FROM openjdk:21
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]