# JDK: Eclipse Temurin 23.0.2, sbt: 1.10.11, Scala: 3.3.6
FROM sbtscala/scala-sbt:eclipse-temurin-alpine-23.0.2_7_1.10.11_3.3.6 AS build

USER sbtuser
WORKDIR /app

# Copy and load the sbt projects so that Docker can cache their dependencies in a layer:
ADD --chown=sbtuser build.sbt /app/
ADD --chown=sbtuser project /app/project/
ADD --chown=sbtuser ABWCF/build.sbt /app/ABWCF/
ADD --chown=sbtuser ABWCF/project /app/ABWCF/project/
RUN sbt reload

# Copy the source files:
ADD --chown=sbtuser src /app/src/
ADD --chown=sbtuser ABWCF/src /app/ABWCF/src/

# Build the JAR:
RUN sbt assembly


FROM eclipse-temurin:23-jre-alpine

ARG OTEL_JAVA_AGENT_VERSION

# Download the OpenTelemetry Java agent:
ADD --chown=sbtuser https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/${OTEL_JAVA_AGENT_VERSION}/opentelemetry-javaagent.jar /opentelemetry/opentelemetry-javaagent.jar

# Copy the build artifacts:
COPY --from=build /app/target/scala-3.3.6/abwcf-dev.jar /app/abwcf-dev.jar

# Run the app:
WORKDIR /app
ENTRYPOINT ["java", "-javaagent:/opentelemetry/opentelemetry-javaagent.jar", "-Dotel.javaagent.configuration-file=/opentelemetry/java-agent.properties", "-jar", "abwcf-dev.jar"]
