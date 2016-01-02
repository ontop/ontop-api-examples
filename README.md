ontop-examples
==============

This repository shows how to use ontop API as Maven libraries.

### Ontop as an OWLAPI library


```xml
<dependency>
	<groupId>it.unibz.inf.ontop</groupId>
	<artifactId>ontop-quest-owlapi3</artifactId>
	<version>1.16.1</version>
</dependency>
```
### Ontop as a Sesame library


```xml
<dependency>
	<groupId>it.unibz.inf.ontop</groupId>
	<artifactId>ontop-quest-sesame</artifactId>
	<version>1.16.1</version>
</dependency>
```

### Ontop with R2RML

If you need to use Ontop with R2RML, put the following dependency
```xml
<dependency>
  <groupId>it.unibz.inf.ontop</groupId>
  <artifactId>ontop-obdalib-r2rml</artifactId>
  <version>1.12.0</version>
</dependency>
```
and add the bolzano maven repo to your `pom.xml`
```xml
	<repositories>	
		<repository>
			<!-- for R2RML api -->
			<id>bolzano-nexus-public</id>
			<url>http://obdavm.inf.unibz.it:8080/nexus/content/groups/public/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
```
