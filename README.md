# ToDo API in Clojure

The intent of this project is to familiarize myself with Clojure. Not only the language, but also the development process.

## Running

1. Start docker `docker compose up -d`
2. Run `make run`
3. It will run on `localhost:3000`

## REPL Development

In `deps.edn` there is an alias `dev` that allow to run the application and interacts with XTDB.

Start the REPL with the `dev` alias and run:

```clj
(start)
```

It will start the server on port `3000` and hot-reload. However, the hot reload doesn't not work so well so some changes require restarting the REPL.

To stop the server, run:

```clj
(stop)
```

### XTDB

Also, you can interact with the XTDB node using

`xt/submit-tx` and `xt/q`. They are already imported in the namespace and there is a global `node`.

Querying all TODOs:

```clj
(xt/q node '(from :todos [*]))
```

## API

This API is composed of the following endpoints:
- `GET /todos`
- `GET /todos/:id`
- `POST /todos`
- `DELETE /todos/:id`
- `PATCH /todos/:id`

### GET /todos/:id

An TODO object looks like:

```json
{
  "description": "my description",
  "completed": false,
  "title": "My task 2",
  "xt/id": "d57087f9-a23f-4e0c-bc9b-1e166dcf75c1"
}
```

### POST /todos

It excepts a payload with `title` and `description`:

```json
{
  "title": "My task 5",
  "description": "my description"
}
```

It creates an object in the `todos` table with `completed = false` and random UUID to `xt/id`

### PATCH /todos/:id

This endpoint allow to update the `title`, `description`, and the `completed` flag:

```json
{
  "completed": false,
  "title": "My new task title",
  "description": "My new task description" 
}
```

## Decisions

I opted for `deps.edn` over `lein` to keep things simple. Lein reminds me a lot of Java projects and after having great DX with Node ecosystem, I want things at least simple.

Using Compojure because seems the most straightforward way of building web applications. However, the reload on file change was tricky to make it work. `ring.middleware.reload` favors variable references and I didn' want to have global variables.

XTDB v2 was used only because of XTQL. I could manage to do all operations with it instead SQL.
