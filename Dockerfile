# ----------- BUILD STAGE -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /project

# Copia apenas o pom para resolver dependências primeiro
COPY pom.xml .

# Baixa dependências (cache)
RUN mvn dependency:go-offline -B

# Agora sim copia o código-fonte
COPY src ./src

# Compila o projeto (gera target/quarkus-app)
RUN mvn package -DskipTests

# ----------- RUNTIME STAGE -----------
FROM registry.access.redhat.com/ubi9/openjdk-21:1.23 AS runtime

WORKDIR /deployments

# Copia o build do Quarkus
COPY --from=build /project/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /project/target/quarkus-app/*.jar /deployments/
COPY --from=build /project/target/quarkus-app/app/ /deployments/app/
COPY --from=build /project/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

# Entrada padrão Quarkus
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
