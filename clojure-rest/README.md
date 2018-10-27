# clojure-rest

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

```shell
lein ring server
```

## Rest API

List all
```shell
curl -X GET "http://localhost:3000/documents"
```

Create document
```shell
curl -X POST \
     -H "Content-Type: application/json" \
     -d '{"title":"some title","text":"and text..."}' \
     "http://localhost:3000/documents"
```

List by id
```shell
curl -X GET "http://localhost:3000/documents/$id"
```

Update document
```shell
curl -X PUT \
     -H "Content-Type: application/json" \
     -d '{"title":"some title","text":"and text..."}' \
     "http://localhost:3000/documents/$id"
```

Delete document
```shell
curl -X DELETE "http://localhost:3000/documents/$id"
```

## References

- https://blog.interlinked.org/programming/clojure_rest.html
