# --- Etapa 1: Construcción del JAR ---
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos archivos del proyecto
COPY pom.xml .
COPY src ./src

# Construimos el proyecto sin tests
RUN mvn clean package -DskipTests


# --- Etapa 2: Imagen final y liviana ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el .jar construido en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto interno del contenedor
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]