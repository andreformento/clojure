FROM clojure:lein-2.8.1

WORKDIR /usr/src/app

COPY project.clj .
RUN lein deps

COPY . .
RUN lein compile
COPY resources/public resources/public

EXPOSE 8080

CMD [ "lein", "ring", "server-headless" ]
