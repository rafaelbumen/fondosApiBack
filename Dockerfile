FROM eclipse-temurin:21-jdk

COPY target/fondosApi-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]


