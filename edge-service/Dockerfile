FROM eclipse-temurin:21-jre
WORKDIR app
COPY build/libs/*SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

