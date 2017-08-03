ontop-api-examples
==============

This repository shows how to use ontop API as Maven libraries.

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
