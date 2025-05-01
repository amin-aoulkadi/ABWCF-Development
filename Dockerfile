# JDK: Eclipse Temurin 23.0.1, sbt: 1.10.7, Scala: 3.3.4
FROM sbtscala/scala-sbt:eclipse-temurin-alpine-23.0.1_11_1.10.7_3.3.4 AS build

USER sbtuser
WORKDIR /app

# Copy and load the sbt projects so that Docker can cache their dependencies in a layer:
ADD --chown=sbtuser build.sbt /app/
ADD --chown=sbtuser project /app/project/
ADD --chown=sbtuser ["Actor-Based Web Crawling Framework/build.sbt", "/app/Actor-Based Web Crawling Framework/"]
ADD --chown=sbtuser ["Actor-Based Web Crawling Framework/project", "/app/Actor-Based Web Crawling Framework/project/"]
RUN sbt reload

# Copy the source files:
ADD --chown=sbtuser src /app/src/
ADD --chown=sbtuser ["Actor-Based Web Crawling Framework/src", "/app/Actor-Based Web Crawling Framework/src/"]

# Build the JAR:
RUN sbt assembly


FROM eclipse-temurin:23-jre-alpine

# Download the OpenTelemetry Java agent:
ADD --chown=sbtuser https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.15.0/opentelemetry-javaagent.jar /opentelemetry/opentelemetry-javaagent.jar

# Copy the build artifacts:
COPY --from=build /app/target/scala-3.3.4/abwcf-dev.jar /app/abwcf-dev.jar

# Run the app:
WORKDIR /app
ENTRYPOINT ["java", "-javaagent:/opentelemetry/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/opentelemetry/java-agent.properties", "-jar", "abwcf-dev.jar"]
