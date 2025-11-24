# ===============================
# Estágio 1: Build (Compilação)
# ===============================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copia apenas o pom.xml primeiro.
# Isso permite que o Docker use cache nas dependências se o pom não mudar.
COPY pom.xml .

# 2. Baixa as dependências (modo offline)
RUN mvn dependency:go-offline -B

# 3. Copia o código fonte
COPY src ./src

# 4. Compila o projeto e gera o .jar (pulando testes para agilizar o build do container)
RUN mvn clean package -DskipTests

# ===============================
# Estágio 2: Run (Execução)
# ===============================
FROM eclipse-temurin:17-jre
WORKDIR /app

# 5. Copia apenas o .jar gerado no estágio anterior
# O curinga (*.jar) pega o nome do arquivo independente da versão
COPY --from=build /app/target/*.jar app.jar

# 6. Expõe a porta (padrao do Spring Boot é 8080, ajuste se necessário)
EXPOSE 8080

# 7. Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
