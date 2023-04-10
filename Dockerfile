#FROM centos as centos
#
#COPY --from=centos /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#RUN echo "Asia/Shanghai" > /etc/timezone

# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app

# 这里看下所有模块的pom文件是否可以拆分出来去执行package，从而利用docker的缓存
#COPY pom.xml .
COPY . ./

ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

# Build a release artifact.
# RUN mvn package -DskipTests


# Run the web service on container startup.
#
# java -Dserver.port=80 -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 /Users/xuyongchang/bilibili/glowworm/java_app/football/booking-web/target/booking-web-1.0-SNAPSHOT.jar --spring.profiles.active=dev

# CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=80","-jar","/app/booking-web/target/booking-web-1.0-SNAPSHOT.jar", "--spring.profiles.active=prod"]

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=80","-jar","/app/booking-web/release/app.jar", "--spring.profiles.active=prod"]

