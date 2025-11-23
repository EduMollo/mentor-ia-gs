# ===========================
#   BUILD STAGE
# ===========================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

# ===========================
#   RUNTIME STAGE
# ===========================
FROM registry.access.redhat.com/ubi9/openjdk-21:1.23

WORKDIR /deployments

COPY --from=build /workspace/target/quarkus-app/lib/ ./lib/
COPY --from=build /workspace/target/quarkus-app/*.jar ./
COPY --from=build /workspace/target/quarkus-app/app/ ./app/
COPY --from=build /workspace/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8080

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

USER 185

ENTRYPOINT [ "java", "-jar", "/deployments/quarkus-run.jar" ]
