# ── Stage 1: Build ───────────────────────────────────────────────────────────
# eclipse-temurin es la imagen oficial de Adoptium, Alpine reduce el tamaño ~60%
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /workspace

# Copiar el wrapper y pom.xml primero para aprovechar el cache de capas de Docker.
# Solo se re-descarga si pom.xml cambia, no cuando cambia el código fuente.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al Maven Wrapper (necesario en Linux)
RUN chmod +x mvnw

# Descargar todas las dependencias offline en una capa separada
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente DESPUÉS de las dependencias (layer cache inteligente)
COPY src/ src/

# Compilar y empaquetar el JAR. -DskipTests evita correr tests en el build de Docker.
RUN ./mvnw package -DskipTests -B

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
# JRE es suficiente para ejecutar (sin herramientas de compilación), más liviano
FROM eclipse-temurin:21-jre-alpine

# Crear usuario no-root por seguridad (buena práctica en contenedores de producción)
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Copiar SOLO el JAR desde la etapa de build (el resto se descarta)
COPY --from=build /workspace/target/*.jar app.jar

# Cambiar al usuario no-root
USER spring

# Puerto por defecto. Render lo sobreescribe dinámicamente con la variable $PORT.
EXPOSE 8080

# -Djava.security.egd: acelera el arranque en Linux al usar /dev/urandom
# El server.port=${PORT:8080} en application.properties toma el valor de $PORT de Render
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
