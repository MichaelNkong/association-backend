# Use official Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy the project source code
COPY . .

# Build the application (creates target/*.jar)
RUN mvn clean package -DskipTests

# Use lightweight OpenJDK 21 image for running the app
FROM openjdk:21
WORKDIR /app

# Copy built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]