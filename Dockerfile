FROM openjdk:17-alpine3.14

RUN apk update


WORKDIR /var/www/project

COPY ./target/project-1.jar /var/www/project

EXPOSE 4700

CMD ["java","-jar","./project-1.jar"]


