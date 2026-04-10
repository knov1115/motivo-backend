# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application (skipping tests to speed up the build)
RUN mvn clean package -DskipTests

# Stage 2: Create the lightweight production image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy ONLY the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the Spring Boot port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]