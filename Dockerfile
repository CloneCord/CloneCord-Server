FROM openjdk:11-jdk-alpine

EXPOSE 80

COPY build/unpacked/dist/BOOT-INF/lib /app/lib
COPY build/unpacked/dist/META-INF /app/META-INF
COPY build/unpacked/dist/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","net.leloubil.clonecord.ClonecordServerApplication"]
