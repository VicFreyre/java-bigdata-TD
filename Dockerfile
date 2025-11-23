# Dockerfile para aplicação Spring Boot (Render)

# Imagem base oficial do Java 17
FROM eclipse-temurin:17-jre

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o jar gerado pelo Maven para o container
COPY target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Variáveis de ambiente para conexão com banco (Render)
ENV SPRING_DATASOURCE_URL=${DATABASE_URL}
ENV SPRING_DATASOURCE_USERNAME=${DATABASE_USER}
ENV SPRING_DATASOURCE_PASSWORD=${DATABASE_PASSWORD}

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
