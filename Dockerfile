FROM openjdk:11.0.5-slim

RUN mkdir /app
WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew build

COPY . .

CMD ["./gradlew", "run"]