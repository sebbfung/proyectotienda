# ==========================================
# ETAPA 1: COMPILACIÓN
# Maven + Java 25
# ==========================================

FROM maven:3.9.16-eclipse-temurin-25-alpine AS build

WORKDIR /app

# Copiar pom.xml primero para aprovechar la caché de Docker
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar el proyecto
RUN mvn clean package -DskipTests


# ==========================================
# ETAPA 2: EJECUCIÓN
# Java 25
# ==========================================

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Crear usuario para ejecutar Spring Boot
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

# Copiar el JAR generado por Maven
COPY --from=build /app/target/*.jar app.jar

# Puerto utilizado por la aplicación
EXPOSE 8080

# Ejecutar Spring Boot
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]