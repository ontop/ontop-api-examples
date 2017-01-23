package org.semanticweb.ontop.examples.books;

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



import it.unibz.inf.ontop.model.OBDAModel;
import it.unibz.inf.ontop.owlrefplatform.core.QuestConstants;
import it.unibz.inf.ontop.owlrefplatform.core.QuestPreferences;
import it.unibz.inf.ontop.owlrefplatform.owlapi.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class QuestOWL_R2RML_Example {

    /*
     * Please use the pre-bundled H2 server from the root of this repository
     *
     */
    final String owlFile = "src/main/resources/example/books/exampleBooks.owl";
    final String r2rmlFile = "src/main/resources/example/books/exampleBooks.ttl";
    final String sparqlFile = "src/main/resources/example/books/q1.rq";


    final String jdbcUrl = "jdbc:h2:tcp://localhost/./books;DATABASE_TO_UPPER=FALSE";
    final String username = "sa";
    final String password = "test";
    final String driverClass = "org.h2.Driver";


    public void runQuery() throws Exception {

        OWLOntology owlOntology = loadOWLOntology(owlFile);


        OBDAModel obdaModel = new MappingLoader().loadRFrom2RMLFile(r2rmlFile, jdbcUrl, username, password, driverClass);

        QuestOWLFactory factory = new QuestOWLFactory();

        QuestPreferences p = new QuestPreferences();
        p.setCurrentValueOf(QuestPreferences.ABOX_MODE, QuestConstants.VIRTUAL);
        p.setCurrentValueOf(QuestPreferences.OBTAIN_FULL_METADATA,
                QuestConstants.FALSE);

        QuestOWLConfiguration config = QuestOWLConfiguration.builder().obdaModel(obdaModel).preferences(p).build();

        String sparqlQuery = loadSPARQL(sparqlFile);


        try (QuestOWL reasoner = factory.createReasoner(owlOntology, config);
             QuestOWLConnection conn = reasoner.getConnection();
             QuestOWLStatement st = conn.createStatement();
             QuestOWLResultSet rs = st.executeTuple(sparqlQuery);
        ) {
            int columnSize = rs.getColumnCount();
            while (rs.nextRow()) {
                for (int idx = 1; idx <= columnSize; idx++) {
                    OWLObject binding = rs.getOWLObject(idx);
                    System.out.print(binding.toString() + ", ");
                }
                System.out.print("\n");
            }

			/*
             * Print the query summary
			 */
            String sqlQuery = st.getUnfolding(sparqlQuery);

            System.out.println();
            System.out.println("The input SPARQL query:");
            System.out.println("=======================");
            System.out.println(sparqlQuery);
            System.out.println();


            System.out.println("The output SQL query:");
            System.out.println("=====================");
            System.out.println(sqlQuery);

        }
    }

    private OWLOntology loadOWLOntology(String owlFile) throws OWLOntologyCreationException {
        OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager();

        return owlManager.loadOntologyFromOntologyDocument(new File(owlFile));
    }


    private String loadSPARQL(String sparqlFile) throws IOException {
        String queryString = "";
        BufferedReader br = new BufferedReader(new FileReader(sparqlFile));
        String line;
        while ((line = br.readLine()) != null) {
            queryString += line + "\n";
        }
        return queryString;
    }

    /**
     * Main client program
     */
    public static void main(String[] args) {
        try {
            QuestOWL_R2RML_Example example = new QuestOWL_R2RML_Example();
            example.runQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
