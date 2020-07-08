package it.unibz.inf.ontop.examples.owlapi;

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

import it.unibz.inf.ontop.iq.IQ;
import it.unibz.inf.ontop.owlapi.OntopOWLFactory;
import it.unibz.inf.ontop.owlapi.OntopOWLReasoner;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLConnection;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLStatement;
import it.unibz.inf.ontop.owlapi.resultset.GraphOWLResultSet;
import it.unibz.inf.ontop.owlapi.resultset.OWLBinding;
import it.unibz.inf.ontop.owlapi.resultset.OWLBindingSet;
import it.unibz.inf.ontop.owlapi.resultset.TupleOWLResultSet;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

public class OntopOWLReasonerNativeMappingExample {

    private static final String owlFile = "src/main/resources/example/books/exampleBooks.owl";
    private static final String obdaFile = "src/main/resources/example/books/exampleBooks.obda";
    private static final String propertyFile = "src/main/resources/example/books/exampleBooks.properties";
    private static final String sparqlFile = "src/main/resources/example/books/q1.rq";
    private static final String constructFile = "src/main/resources/example/books/q2.rq";

    /**
     * Main client program
     */
    public static void main(String[] args) throws OWLException, IOException {
        OntopOWLReasonerNativeMappingExample example = new OntopOWLReasonerNativeMappingExample();
        example.run();
    }

    public void run() throws OWLException, IOException {

        OntopOWLFactory factory = OntopOWLFactory.defaultFactory();

        OntopSQLOWLAPIConfiguration config = OntopSQLOWLAPIConfiguration.defaultBuilder()
                .nativeOntopMappingFile(obdaFile)
                .ontologyFile(owlFile)
                .propertyFile(propertyFile)
                .enableTestMode()
                .build();

        OntopOWLReasoner reasoner = factory.createReasoner(config);

		/*
         * Prepare the data connection for querying.
		 */
        String sparqlQuery = Files.lines(Paths.get(sparqlFile)).collect(joining("\n"));

        System.out.println();
        System.out.println("The input SPARQL query:");
        System.out.println("=======================");
        System.out.println(sparqlQuery);
        System.out.println();

        try (OntopOWLConnection conn = reasoner.getConnection();
             OntopOWLStatement st = conn.createStatement();
             TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery)
        ) {
            System.out.println(rs.getSignature().stream().collect(joining(",")));
            System.out.println("------------------------------------------------------------------------------------------");

            while (rs.hasNext()) {
                final OWLBindingSet bindingSet = rs.next();
                for (OWLBinding binding : bindingSet) {
                    OWLObject value = binding.getValue();
                    System.out.print(ToStringRenderer.getInstance().getRendering(value) + ", ");
                }
                System.out.print("\n");
            }

            // Only for debugging purpose, not for end users: this will redo the query reformulation, which can be expensive
            IQ executableQuery = st.getExecutableQuery(sparqlQuery);
            String sqlQuery = executableQuery.toString();

            System.out.println("The output SQL query:");
            System.out.println("=====================");
            System.out.println(sqlQuery);

        }

        String constructQuery = Files.lines(Paths.get(constructFile)).collect(joining("\n"));

        System.out.println();
        System.out.println("The input construct SPARQL query:");
        System.out.println("=======================");
        System.out.println(constructQuery);
        System.out.println();

        try (OntopOWLConnection conn = reasoner.getConnection();
             OntopOWLStatement st = conn.createStatement();
             GraphOWLResultSet rs = st.executeConstructQuery(constructQuery)
        ) {
            System.out.println("------------------------------------------------------------------------------------------");

            while (rs.hasNext()) {
                OWLAxiom owlAxiom = rs.next();

                System.out.print(owlAxiom);
            }
        }
    }


}
