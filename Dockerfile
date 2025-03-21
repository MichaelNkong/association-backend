# Use OpenJDK 21 as the base image
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/association-backend-0.0.1-SNAPSHOT.jar app.jar



# Run the application
CMD ["java", "-jar", "app.jar"]
