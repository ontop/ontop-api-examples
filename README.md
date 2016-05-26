ontop-api-examples
==============

This repository shows how to use ontop API as Maven libraries.

## Test database

A sample h2 database is provided in the `h2` directory. To start it, just

```
$ cd h2
$ ./h2.sh
```
A login window will popup. The login infos are:

- Saved Settings: `Generic H2 (Server)`
- Driver Class: `org.h2.Driver`
- User Name: `sa`
- Password: `test`

## Ontop Maven artifacts

### Ontop as an OWLAPI library


```xml
<dependency>
	<groupId>it.unibz.inf.ontop</groupId>
	<artifactId>ontop-quest-owlapi</artifactId>
	<version>1.18.0</version>
</dependency>
```
### Ontop as a Sesame library


```xml
<dependency>
	<groupId>it.unibz.inf.ontop</groupId>
	<artifactId>ontop-quest-sesame</artifactId>
	<version>1.18.0</version>
</dependency>
```