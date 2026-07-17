# url-shortener

A URL shortener built with Spring Boot. Takes a long URL and returns a short
code; opening the short link redirects to the original.

## Stack
- Spring Boot 3, Java 17
- MySQL (stores the long URLs)
- Redis (caches redirects)

## How it works
- The short code is the database id encoded in Base62 — it is not stored
  separately, it is decoded back to the id on lookup.
- Shortening is idempotent: the same long URL always returns the same code.
- Redis caches the code to URL mapping (1-hour TTL) so repeat redirects skip
  the database (cache-aside pattern).

## API
- `POST /shorten` — body `{ "url": "..." }`, returns `{ "shortCode": "..." }`
- `GET /{code}` — 302 redirect to the original URL

There is also a small web page at `/` to shorten and open links.

## Run locally
Needs Java 17, MySQL, and Redis. Create a database `urlshortener`, set the DB
password via the `MYSQLPASSWORD` env var, then run `mvn spring-boot:run` and
open http://localhost:8080
