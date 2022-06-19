# DDD repositories with jOOQ

This sample project shows how jOOQ can help implementing DDD repositories without leaking any technical information in
the domain layer:

- In [domain/User.java](/src/main/java/fr/vernoux/dddjooq/domain/User.java)
  and [domain/Backlog.java](/src/main/java/fr/vernoux/dddjooq/domain/Backlog.java), there is no entity annotation and no
  constructor dedicated to the persistence layer.
- Since [domain/User.java](/src/main/java/fr/vernoux/dddjooq/domain/User.java) is flat (no nested object or collection)
  , [infrastructure/JooqUserRepository.java](/src/main/java/fr/vernoux/dddjooq/infrastructure/JooqUserRepository.java)
  is implemented very simply using jOOQ's CRUD APIs.
- On the other hand, since [domain/Backlog.java](/src/main/java/fr/vernoux/dddjooq/domain/Backlog.java) contains a
  nested collection of UserStory objects, which don't even have their own
  id. [infrastructure/JooqBacklogRepository.java](/src/main/java/fr/vernoux/dddjooq/infrastructure/JooqBacklogRepository.java)
  shows how jOOQ solves this issue quite elegantly.

To see the magic yourself:

- Run a Postgres instance, for instance with the provided [Docker Compose file](docker-compose.yml)
- Change the Postgres connection settings, if necessary, in the [pom.xml](pom.xml) (properties `db.url`, `db.user`
  and `db.password`) and [application.yaml](/src/main/resources/application.yaml).
- Run `mvn compile`. Thiw will run the flyway migration (creating the DB schema) and generate the jOOQ classes from this
  schema.
- Run the provided tests.