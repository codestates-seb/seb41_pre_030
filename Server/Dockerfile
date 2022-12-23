FROM openjdk:11
COPY build/libs/*.jar seb-pre-be-30.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/seb-pre-be-30.jar"]