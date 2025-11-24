# Etapa build
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Corregido: -DskipTests para saltar los tests y evitar errores si no hay BD conectada al construir
RUN mvn clean package -DskipTests

# Etapa Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Asegúrate de que el nombre coincida. El * atrapa cualquier nombre, lo cual está bien.
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]