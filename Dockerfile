FROM eclipse-temurin:21-jdk

# Cria a pasta da aplicação no container
WORKDIR /app

# Copia o JAR gerado
COPY target/api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
