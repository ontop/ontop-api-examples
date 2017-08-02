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
import org.eclipse.rdf4j.query.AbstractTupleQueryResultHandler;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OntopRDF4JNativeMappingExample {

    private static final String owlFile = "src/main/resources/example/books/exampleBooks.owl";
    private static final String obdaFile = "src/main/resources/example/books/exampleBooks.obda";
    private static final String propertyFile = "src/main/resources/example/books/exampleBooks.properties";
    private static final String sparqlFile = "src/main/resources/example/books/q1.rq";

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

    public void run() throws Exception {
        String queryString = loadSPARQL(sparqlFile);


        System.out.println();
        System.out.println("The input SPARQL query:");
        System.out.println("=======================");
        System.out.println(queryString);
        System.out.println();

        OntopSQLOWLAPIConfiguration configuration = OntopSQLOWLAPIConfiguration.defaultBuilder()
                .ontologyFile(owlFile)
                .nativeOntopMappingFile(obdaFile)
                .propertyFile(propertyFile)
                .enableTestMode()
                .build();

        OntopRepository repo = OntopRepository.defaultRepository(configuration);

        repo.initialize();

        try (
             RepositoryConnection conn = repo.getConnection()
        ) {
            final TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);

            tupleQuery.evaluate(new AbstractTupleQueryResultHandler() {
                @Override
                public void handleSolution(BindingSet bindingSet) throws TupleQueryResultHandlerException {
                    System.out.println(bindingSet);
                }
            });


            // execute query
//            Query query = conn.prepareQuery(QueryLanguage.SPARQL, queryString);
//
//            TupleQuery tq = (TupleQuery) query;
//
//            TupleQueryResult result = tq.evaluate();
//
//            while (result.hasNext()) {
//                final BindingSet bindingSet = result.next();
//                for (Binding binding : bindingSet) {
//                    System.out.print(binding.getValue() + ", ");
//                }
//                System.out.println();
//            }
        }

        repo.shutDown();
    }

    private String loadSPARQL(String sparqlFile) throws IOException {
        StringBuilder queryString = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(sparqlFile));
        String line;
        while ((line = br.readLine()) != null) {
            queryString.append(line).append("\n");
        }
        return queryString.toString();
    }


}
