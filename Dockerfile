FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.12_7_openj9-0.27.0-alpine-slim as builder

WORKDIR app
 
ARG JAR_FILE=target/*.jar
RUN echo $JAR_FILE 

COPY ${JAR_FILE} /opt/app/app.jar
RUN java -Djarmode=layertools -jar /opt/app/app.jar extract

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.12_7_openj9-0.27.0-alpine-slim
RUN mkdir -p /opt/app/ssl \
&& mkdir -p /opt/app/static \
&& mkdir -p /opt/app/config \
&& mkdir -p /opt/app/data \
&& mkdir -p /opt/app/cache \
&& mkdir -p /opt/app/logs
WORKDIR /opt/app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ARG JAVA_OPTS=""
RUN echo $JAVA_OPTS 

# ENTRYPOINT ["java","-cp","/opt/app","-Djava.security.egd=file:/dev/./urandom","-Djava.net.preferIPv4Stack=true", "org.springframework.boot.loader.JarLauncher"]
ENTRYPOINT ["sh", "-c", \
"java -server --enable-preview -XX:+UseContainerSupport \
-XX:+AlwaysActAsServerClassMachine -XX:+UseG1GC -XX:+UseStringDeduplication ${JAVA_OPTS} \
org.springframework.boot.loader.JarLauncher ${0} ${@}"]
