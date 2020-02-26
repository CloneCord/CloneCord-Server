FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app
USER spring:spring
EXPOSE 80

COPY build/unpacked/dist/BOOT-INF/lib /app/lib
COPY build/unpacked/dist/META-INF /app/META-INF
COPY build/unpacked/dist/BOOT-INF/classes /app

ENTRYPOINT ["java","-Xverify:none","-cp","./:lib/*","net.leloubil.clonecordserver.ClonecordServerApplication"]
