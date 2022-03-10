####################################################
FROM maven:3.8.4-openjdk-11-slim as builder
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD rnis-simulator/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
ADD rnis-simulator/src $PROJECT_DIR/src
RUN mvn package
#####################################################
FROM openjdk:11-jre-slim as runtime
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
EXPOSE 8081
COPY --from=builder $PROJECT_DIR/target/simulator.jar $PROJECT_DIR/
CMD ["java", "-jar", "/opt/project/simulator.jar"]