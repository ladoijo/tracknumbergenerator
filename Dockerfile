LABEL authors="hadyanyasrizal"

# Stage 1: Build the application
FROM gradle:8.14-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# Stage 2: Create a slim runtime image
FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*-layers/dependencies ./
COPY --from=build /home/gradle/project/build/libs/*-layers/spring-boot-loader ./
COPY --from=build /home/gradle/project/build/libs/*-layers/snapshot-dependencies ./
COPY --from=build /home/gradle/project/build/libs/*-layers/application ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]