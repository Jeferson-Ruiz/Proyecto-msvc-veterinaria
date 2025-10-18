FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

ADD ./target/sav-mvsc-medicalcontrol-0.0.1-SNAPSHOT.jar msvc-medicalcontrol.jar
ENTRYPOINT [ "java", "-jar", "msvc-medicalcontrol.jar" ]