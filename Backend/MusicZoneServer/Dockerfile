FROM maven:3.9.0-eclipse-temurin-17-alpine AS builder
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
WORKDIR "/app"
COPY --from=builder /target/*.jar /MusicZoneServer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/MusicZoneServer-0.0.1-SNAPSHOT.jar"]


#FROM maven:3.8.5-openjdk-17 AS builder
#
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#COPY src ./src
#
## Собираем приложение
#RUN mvn package -DskipTests
#
## Второй этап - создание образа с минимальным размером
#FROM openjdk:8u342-jre
#
## Устанавливаем рабочую директорию внутри контейнера
#WORKDIR /app
#
## Копируем JAR-файл приложения из предыдущего этапа
#COPY --from=builder /app/target/MusicZoneServer-0.0.1-SNAPSHOT.jar MusicZoneServer-0.0.1-SNAPSHOT.jar
#
## Запускаем приложение при старте контейнера
#CMD ["java", "-jar", "MusicZoneServer-0.0.1-SNAPSHOT.jar"]