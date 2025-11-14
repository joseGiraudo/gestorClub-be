# --- Etapa 1: Construcción del JAR ---
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos los archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto sin tests
RUN mvn clean package -DskipTests


# --- Etapa 2: Imagen final (más liviana) ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el .jar construido
COPY --from=build /app/target/*.jar app.jar

# Variable para activar el perfil de Spring
ENV SPRING_PROFILES_ACTIVE=postgres

# Exponemos el puerto (Spring Boot usa 8080)
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
