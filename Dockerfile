# Imagem base com JDK
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR do projeto
COPY target/gestao-estoque-distribuido-controller-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
