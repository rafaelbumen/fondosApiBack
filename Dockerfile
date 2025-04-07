FROM eclipse-temurin:21-jdk
COPY target/fondos-api.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
