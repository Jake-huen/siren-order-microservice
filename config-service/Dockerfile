FROM openjdk:17-ea-11-jdk-slim
EXPOSE 8888
COPY apiEncryptionKey.jks apiEncryptionKey.jks
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV SPRING_RABBITMQ_HOST=rabbitmq
ENV SPRING_PROFILES_ACTIVE=default
ENTRYPOINT ["java","-jar","app.jar"]