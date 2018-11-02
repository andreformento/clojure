# clojure-rest

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running all with docker

```shell
docker-compose up --build
```

Go to http://localhost:8080

## Running local

Start Mongo
```shell
docker-compose up -d mongo
```

Test:

```shell
lein test
```

To start a web server for the application, run:

```shell
lein ring server 8080
```

## References

- https://blog.interlinked.org/programming/clojure_rest.html
- https://github.com/metosin/compojure-api/blob/master/examples/simple/README.md
- https://github.com/metosin/compojure-api/blob/master/examples/resources/src/example/handler.clj
