FROM openjdk:17
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/projeto-springboot.jar
WORKDIR /app
ENTRYPOINT java -jar projeto-springboot.jar