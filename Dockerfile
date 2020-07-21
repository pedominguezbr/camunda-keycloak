# maven build
FROM maven:3.6.1-jdk-11-slim AS MAVEN_TOOL_CHAIN
COPY pom-docker.xml /tmp/pom.xml
COPY src /tmp/src/
COPY settings-docker.xml /usr/share/maven/ref/
WORKDIR /tmp/
RUN mvn -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

# jlinked java 11 (do NOT use alpine-slim here which has important module files deleted)
#FROM adoptopenjdk/openjdk11:jdk-11.0.3_7-alpine AS JLINKED_JAVA
#RUN ["jlink", "--compress=2", \
#     "--module-path", "/opt/java/openjdk/jmods", \
#     "--add-modules", "java.base,java.compiler,java.desktop,java.instrument,java.management,java.prefs,java.rmi,java.scripting,java.security.jgss,java.security.sasl,java.sql.rowset,jdk.httpserver,jdk.jdi,jdk.unsupported", \
#     "--output", "/jlinked"]

# final custom slim java image (for apk command see jdk-11.0.3_7-alpine-slim)
#FROM us.icr.io/image-base/alpine:3.9-with-buildeps
#ENV JAVA_VERSION jdk-11.0.3+7
#COPY --from=JLINKED_JAVA /jlinked /opt/java/openjdk
#ENV JAVA_HOME=/opt/java/openjdk \
#PATH="/opt/java/openjdk/bin:$PATH"

# add spring boot application
#VOLUME /tmp
#COPY --from=MAVEN_TOOL_CHAIN /tmp/target/camunda-bpm-identity-keycloak-examples-sso-kubernetes*.jar ./app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM adoptopenjdk/openjdk11:jdk-11.0.3_7-alpine

RUN ["jlink", "--compress=2", \
     "--module-path", "/opt/java/openjdk/jmods", \
     "--add-modules", "java.base,java.compiler,java.desktop,java.instrument,java.management,java.prefs,java.rmi,java.scripting,java.security.jgss,java.security.sasl,java.sql.rowset,jdk.httpserver,jdk.jdi,jdk.unsupported", \
     "--output", "/jlinked"]
# Refer to Maven build -> finalName
#ARG JAR_FILE=target/spring-boot-web.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/ventadesagregada*.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]
