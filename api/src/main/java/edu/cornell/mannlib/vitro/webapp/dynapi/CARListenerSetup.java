package edu.cornell.mannlib.vitro.webapp.dynapi;

import edu.cornell.mannlib.vitro.webapp.modelaccess.ModelAccess;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFService;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CARListenerSetup implements ServletContextListener {
    private static final Log log = LogFactory.getLog(CARListenerSetup.class);
    private static CARListener changeListener = null;

    /**
     * Register a change listener with the RDFService
     *
     * @param ctx
     */
    private synchronized void registerChangeListener(ServletContext ctx) {
        // Check that no change listener has already been created
        if (changeListener == null) {
            // Get the RDF Service
            RDFService rdfService = ModelAccess.on(ctx).getRDFService();
            try {
                // Create a change listener
                changeListener = new CARListener();

                // Register the change listener
                rdfService.registerListener(changeListener);
            } catch (RDFServiceException e) {
                log.error(e);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Ensure the change listener is registered with the RDFService
        registerChangeListener(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
