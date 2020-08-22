ontop-api-examples
==============

This repository shows how to use ontop API as Maven libraries.

NOTICE
-----

- We now recommend the usage of the [Docker image](https://hub.docker.com/repository/docker/ontop/ontop-endpoint), or as a second option, the [CLI](https://ontop-vkg.org/guide/cli.html#ontop-endpoint).
- Ontop Java API is not intended to be used in production. We reserve ourselves the right to change dependencies and JVM versions for newer versions so please do not consider the  current requirements as stable.
- The OWLAPI bindings should only be used by those interested in having access to
 some internals. Its querying interface is actually internal to us, so please consider
  the RDF4J API for a more standard and stable interface.

## Test database

A sample h2 database is provided in the `h2` directory. To start it, just

```bash
$ cd h2
$ ./h2.sh
```
A login window will popup. The login infos are:

- Saved Settings: `Generic H2 (Server)`
- Driver Class: `org.h2.Driver`
- User Name: `sa`
- Password: `test`

## Ontop Maven artifacts

### Ontop as a RDF4J library (recommended)

```xml
        <dependency>
            <groupId>it.unibz.inf.ontop</groupId>
            <artifactId>ontop-rdf4j</artifactId>
            <version>${ontop.version}</version>
        </dependency>
```

```xml
        <dependency>
            <groupId>it.unibz.inf.ontop</groupId>
            <artifactId>ontop-system-sql-owlapi</artifactId>
            <version>${ontop.version}</version>
        </dependency>
```

### Ontop as an OWL-API library

```xml
        <dependency>
            <groupId>it.unibz.inf.ontop</groupId>
            <artifactId>ontop-owlapi</artifactId>
            <version>${ontop.version}</version>
        </dependency>
```
        
```xml
        <dependency>
            <groupId>it.unibz.inf.ontop</groupId>
            <artifactId>ontop-system-sql-owlapi</artifactId>
            <version>${ontop.version}</version>
        </dependency>
```

