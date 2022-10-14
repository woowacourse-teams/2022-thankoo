FROM openjdk:11
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} thankoo.jar
ENTRYPOINT ["java","-jar","/thankoo.jar", "-Duser.timezone='Asia/Seoul'", "-Dspring.profiles.active=local", "--spring.config.location=classpath:/thankoo-backend-secret/application-local.yml"]
