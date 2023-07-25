FROM openjdk:17-jdk-slim
COPY target/normalizer.jar normalizer.jar
ENTRYPOINT ["java", "-jar", "/normalizer.jar"]