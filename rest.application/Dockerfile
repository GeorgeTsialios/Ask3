FROM adoptopenjdk/openjdk13-openj9:latest
MAINTAINER ece.upatras.gr
COPY deploy/rest.application-0.0.1-SNAPSHOT-exec.jar /opt/rest.application/
WORKDIR /opt/rest.application/
CMD ["java", "-jar", "/opt/rest.application/rest.application-0.0.1-SNAPSHOT-exec.jar"]
EXPOSE 8888
