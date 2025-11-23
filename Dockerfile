 Etapa de build: Maven + Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa final: apenas Java 17
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Variáveis de ambiente para Render
ENV SPRING_DATASOURCE_URL=${DATABASE_URL}
ENV SPRING_DATASOURCE_USERNAME=${DATABASE_USER}
ENV SPRING_DATASOURCE_PASSWORD=${DATABASE_PASSWORD}
ENTRYPOINT ["java", "-jar", "app.jar"]
