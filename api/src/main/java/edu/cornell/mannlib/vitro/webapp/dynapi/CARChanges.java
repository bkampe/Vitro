package edu.cornell.mannlib.vitro.webapp.dynapi;

import edu.cornell.mannlib.vitro.webapp.rdfservice.ModelChange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.model.*;

public class CARChanges extends Thread{
    private static final Log log = LogFactory.getLog(CARChanges.class);

    private ModelChange.Operation operation;
    private Model changes;

    public CARChanges(ModelChange.Operation operation, Model changes) {
        this.operation = operation;
        this.changes = changes;
    }

    public void run() {

        log.debug("Data handling started");
        log.debug("Operation: "+operation);

        // list the statements in the graph
        StmtIterator iter = changes.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement   stmt      = iter.nextStatement();   // get next statement
            Resource    subject   = stmt.getSubject();      // get the subject
            Property    predicate = stmt.getPredicate();    // get the predicate
            RDFNode     object    = stmt.getObject();       // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object);
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
    }
}
