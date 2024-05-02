# ToDo API in Clojure

The intent of this project is to familiarize myself with Clojure. Not only the language, but also the development process.

## API

This API is composed of the following endpoints:
- `GET /todos`
- `GET /todos/{id}`
- `POST /todos`
- `PUT /todos/{id}`
- `DELETE /todos/{id}`
- `PATCH /todos/{id}`

## Decisions

I opted for `deps.edn` over `lein` to keep things simple. Lein reminds me a lot of Java projects and after having great DX with Node ecosystem, I want things at least simple.

Using Compojure because seems the most straightforward way of building web applications
