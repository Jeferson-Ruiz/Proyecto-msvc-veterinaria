FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

ADD ./target/msvc-sav-workstaff-0.0.1-SNAPSHOT.jar msvc-workstaff.jar
ENTRYPOINT [ "java", "-jar","msvc-workstaff.jar" ]

