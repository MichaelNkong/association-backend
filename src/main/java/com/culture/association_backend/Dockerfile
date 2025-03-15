# Use OpenJDK 21 instead of 17
FROM openjdk:21

# Set working directory
WORKDIR /app

# Copy the built JAR file (ensure it's built first!)
COPY target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]