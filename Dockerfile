# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY . ./

# Build a release artifact.
RUN mvn package -DskipTests


# Run the web service on container startup.
#
# java -Dserver.port=80 -jar /Users/xuyongchang/bilibili/glowworm/java_app/football/booking-web/target/booking-web-1.0-SNAPSHOT.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=80","-jar","/app/booking-web/target/booking-web-1.0-SNAPSHOT.jar", "--spring.profiles.active=prod"]

