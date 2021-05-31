FROM openjdk:latest
EXPOSE 8080
ADD ./target/blog-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]