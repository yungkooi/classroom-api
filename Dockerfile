FROM openjdk:8

COPY target/classroomapi-1.0.jar classroomapi-1.0.jar

ENTRYPOINT ["java", "-jar", "/classroomapi-1.0.jar"]
