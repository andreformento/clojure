FROM ubuntu:22.10 as downloader

ARG DATOMIC_USER
ARG DATOMIC_PASSWORD
ARG DATOMIC_VERSION 1.0.6527

RUN apt update && apt install -y wget unzip

RUN wget --http-user=$DATOMIC_USER \
         --http-password=$DATOMIC_PASSWORD \
         https://my.datomic.com/repo/com/datomic/datomic-pro/$DATOMIC_VERSION/datomic-pro-$DATOMIC_VERSION.zip \
         -O /datomic-pro.zip

RUN unzip /datomic-pro.zip -d /datomic-pro-parent && \
    mv /datomic-pro-parent/datomic* /datomic-pro

RUN cat /datomic-pro/config/samples/dev-transactor-template.properties | grep -v license-key > /datomic-pro/config/dev-transactor-template.properties

FROM openjdk:19-jdk

ENV DATOMIC_LICENSE_KEY ""

COPY --from=downloader /datomic-pro /datomic-pro
COPY start.sh .

EXPOSE 4334

ENTRYPOINT ["/start.sh"]
