package it.unibz.inf.ontop.examples.rdf4j;

/*
 * #%L
 * ontop-quest-owlapi3
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration;
import it.unibz.inf.ontop.rdf4j.repository.OntopRepository;
import it.unibz.inf.ontop.rdf4j.repository.OntopRepositoryConnection;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

public class OntopRDF4JNativeMappingExample {

    private static final String owlFile = "src/main/resources/example/books/exampleBooks.owl";
    private static final String obdaFile = "src/main/resources/example/books/exampleBooks.obda";
    private static final String propertyFile = "src/main/resources/example/books/exampleBooks.properties";
    private static final String sparqlFile = "src/main/resources/example/books/q1.rq";
    private static final String constructFile = "src/main/resources/example/books/q2.rq";

    /**
     * Main client program
     */
    public static void main(String[] args) {
        try {
            OntopRDF4JNativeMappingExample example = new OntopRDF4JNativeMappingExample();
            example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        String sparqlQuery = Files.readString(Paths.get(sparqlFile));
        System.out.println();
        System.out.println("The input SPARQL query:");
        System.out.println("=======================");
        System.out.println(sparqlQuery);
        System.out.println();

        OntopSQLOWLAPIConfiguration configuration = OntopSQLOWLAPIConfiguration.defaultBuilder()
                .ontologyFile(owlFile)
                .nativeOntopMappingFile(obdaFile)
                .propertyFile(propertyFile)
                .enableTestMode()
                .build();

        Repository repo = OntopRepository.defaultRepository(configuration);
        repo.init();

        try (
                RepositoryConnection conn = repo.getConnection() ;
                TupleQueryResult result = conn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate()
        ) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                System.out.println(bindingSet);
            }

            // Only for debugging purpose, not for end users: this will redo the query reformulation, which can be expensive
            //String sqlQuery = ((OntopRepositoryConnection) conn).reformulate(sparqlQuery);
            String sqlQuery = ((OntopRepositoryConnection) conn).reformulateIntoNativeQuery(sparqlQuery);
            System.out.println();
            System.out.println("The reformulated SQL query:");
            System.out.println("=======================");
            System.out.println(sqlQuery);
            System.out.println();
        }
        
        String sparqlConstructQuery = Files.readString(Paths.get(constructFile));

        System.out.println();
        System.out.println("The input SPARQL construct query:");
        System.out.println("=======================");
        System.out.println(sparqlConstructQuery);
        System.out.println();
        
        try (
                RepositoryConnection conn = repo.getConnection() ;
                GraphQueryResult result = conn.prepareGraphQuery(QueryLanguage.SPARQL, sparqlConstructQuery).evaluate()
        ) {
            while (result.hasNext()) {
                Statement statement = result.next();
                System.out.println(statement);
                result.close();
            }
        }

        repo.shutDown();
    }

}
