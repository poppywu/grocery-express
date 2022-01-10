FROM openjdk:11
COPY . /backend
WORKDIR /backend
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw