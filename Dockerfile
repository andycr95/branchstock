## Build stage
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

RUN ls -la /home/app/target/

FROM openjdk:17-slim
COPY --from=build /home/app/target/*.jar /app/branch_stock.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/branch_stock.jar"]