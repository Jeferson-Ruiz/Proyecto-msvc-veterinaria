FROM amazoncorretto:17-alpine-jdk
EXPOSE 9100

WORKDIR /app
ADD ./target/msvc-oauth-0.0.1-SNAPSHOT.jar msvc-oauth.jar
ENTRYPOINT [ "java", "-jar", "msvc-oauth.jar"]