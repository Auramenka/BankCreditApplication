FROM openjdk:21

WORKDIR /app

COPY build/libs/task-1-1.0-SNAPSHOT-all.jar app.jar

RUN mkdir task-1

COPY data/ task-1/data/

CMD ["java", "-jar", "app.jar"]