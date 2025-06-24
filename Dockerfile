# ---- Stage 1: Build Stage ----
# Use an official OpenJDK runtime as a parent image for the build stage.
# The 'eclipse-temurin' images are recommended by the Spring Boot team.
FROM eclipse-temurin:21-jdk-jammy AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper files, build scripts, and settings file.
# This leverages Docker's layer caching. These files don't change often.
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

# Copy the rest of the application source code
COPY src ./src

# Grant execute permission to the gradlew script
RUN chmod +x ./gradlew

# Build the application using the Gradle wrapper.
# -x test skips running the tests during the Docker build.
RUN ./gradlew build -x test


# ---- Stage 2: Production Stage ----
# Use a minimal JRE image for the final production image.
# 'jammy' is based on Ubuntu 22.04 LTS.
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the application JAR from the builder stage.
# The JAR is located in the build/libs directory.
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
# Using exec form to run as the main process (PID 1)
ENTRYPOINT ["java", "-jar", "app.jar"]
