# ==========================================
# Minimal Production JRE Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Run as non-root user for security
RUN addgroup -S civilshield && adduser -S civilshield -G civilshield
USER civilshield

# Copy pre-built standalone shadow JAR
COPY --chown=civilshield:civilshield app.jar app.jar

# Google Cloud Run injects PORT environment variable dynamically
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
