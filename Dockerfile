FROM ubuntu:18.04

RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get -y install default-jre

EXPOSE 20001

ENV JAR QuotesManagementClient-0.0.1-SNAPSHOT.jar

ADD /target/${JAR} ${JAR}
