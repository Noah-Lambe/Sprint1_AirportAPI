#build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

#copy wrapper + pom
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn
COPY pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -DskipTests dependency:go-offline

#copy sources and build
COPY src ./src
RUN ./mvnw -q -DskipTests package

#run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport"
EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -jar app.jar"]
