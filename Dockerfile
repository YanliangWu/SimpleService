# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Build the Gradle project
RUN ./gradlew build

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "build/libs/SimpleService-0.0.1-SNAPSHOT.jar"]