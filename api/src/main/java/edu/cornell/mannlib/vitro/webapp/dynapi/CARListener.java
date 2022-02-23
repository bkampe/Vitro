package edu.cornell.mannlib.vitro.webapp.dynapi;

import edu.cornell.mannlib.vitro.webapp.rdfservice.ChangeListener;
import edu.cornell.mannlib.vitro.webapp.rdfservice.ModelChange;
import edu.cornell.mannlib.vitro.webapp.rdfservice.impl.RDFServiceUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.rdf.listeners.StatementListener;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelChangedListener;

public class CARListener extends StatementListener implements ModelChangedListener, ChangeListener {
    private static final Log log = LogFactory.getLog(CARListener.class);
    private CARChanges carChanges;
    @Override
    public void notifyModelChange(ModelChange modelChange) {
        // Convert the serialized statements into a Jena Model
        Model changes = RDFServiceUtils.parseModel(modelChange.getSerializedModel(), modelChange.getSerializationFormat());

        // TODO filter for relevant changes

        // give changes to change handler
        if (!changes.isEmpty()) {
            carChanges = new CARChanges(modelChange.getOperation(), changes);
            carChanges.run();
        }

    }

    @Override
    public void notifyEvent(String graphURI, Object event) {

    }
}
