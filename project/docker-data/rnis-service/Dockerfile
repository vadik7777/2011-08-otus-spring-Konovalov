####################################################
FROM maven:3.8.4-openjdk-11-slim as builder
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD rnis-integration/pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
ADD rnis-integration/src $PROJECT_DIR/src
RUN mvn package
#####################################################
FROM openjdk:11-jre-slim as runtime
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
EXPOSE 8080
COPY ./docker-data/rnis-service/run_app.sh $PROJECT_DIR/run_app.sh
COPY --from=builder $PROJECT_DIR/target/rnis.jar $PROJECT_DIR/
ENTRYPOINT ["/bin/bash", "-c", "/opt/project/run_app.sh"]