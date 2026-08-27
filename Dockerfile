# ==========================================
# Stage 1: Build the Server Shadow Fat JAR
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Install necessary tools for build
RUN apk add --no-cache bash

# Copy gradle wrapper and configuration files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts gradle.properties ./

# Minimal settings for backend: only include :core and :server
RUN echo 'rootProject.name = "CivilShield"' > settings.gradle.kts && \
    echo 'enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")' >> settings.gradle.kts && \
    echo 'pluginManagement { repositories { google { mavenContent { includeGroupAndSubgroups("androidx"); includeGroupAndSubgroups("com.android"); includeGroupAndSubgroups("com.google") } }; mavenCentral(); gradlePluginPortal() } }' >> settings.gradle.kts && \
    echo 'dependencyResolutionManagement { repositories { google { mavenContent { includeGroupAndSubgroups("androidx"); includeGroupAndSubgroups("com.android"); includeGroupAndSubgroups("com.google") } }; mavenCentral() } }' >> settings.gradle.kts && \
    echo 'include(":core")' >> settings.gradle.kts && \
    echo 'include(":server")' >> settings.gradle.kts

# Grant execution permissions
RUN chmod +x ./gradlew

# Copy ONLY backend-related modules (core and server)
COPY core core
COPY server server

# Build the standalone Shadow Fat JAR
RUN ./gradlew :server:shadowJar --no-daemon

# ==========================================
# Stage 2: Minimal Production JRE Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

# Run as non-root user
RUN addgroup -S civilshield && adduser -S civilshield -G civilshield
USER civilshield

# Copy standalone shadow JAR from builder stage
COPY --from=builder --chown=civilshield:civilshield /app/server/build/libs/server-all.jar app.jar

# Google Cloud Run injects PORT environment variable dynamically
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
