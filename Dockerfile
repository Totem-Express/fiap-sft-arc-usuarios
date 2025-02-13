FROM eclipse-temurin:22

RUN mkdir /opt/app

COPY target/totem-express.jar /opt/app

WORKDIR /opt/app
CMD ["java", "-jar", "totem-express.jar"]
