package databaseConnect.api.persistence.db;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RMT2Base;


import databaseConnect.api.ConnectionProvider;
import databaseConnect.api.persistence.DatabaseException;

public abstract class AbstractConnectionProviderImpl extends RMT2Base implements ConnectionProvider {
	public static final Logger logger = LogManager.getLogger(AbstractConnectionProviderImpl.class);

	    /**
	     * This is the name of the key that is found in
	     * <i>DB-ORM-Config.properites</i> that points to a specific JDBC Resource.
	     * <p>
	     * This variable can represent a JDBC resource in a variety of ways. For
	     * example, it could be the name of JNDI Daatasource, a LDAP Distinguish
	     * Name, the full package of a .properties file, or etc.
	     */
	    protected static final String RESOURCE_KEY_NAME = "DbResourceName";

	    private Properties config;

	    private String contextName;

	    /**
	     * Default constructor
	     */
	    public AbstractConnectionProviderImpl() {
	        super();
	    }

	    /**
	     * Loads the DB ORM connection configuration properteis file as a
	     * ResourceBundle
	     * 
	     * @throws DatabaseException
	     * @throws SystemException
	     */
	    public void init() throws DatabaseException {
	        logger.info(AbstractConnectionProviderImpl.class.getName()
	                + " is initialized");
	    }

	    /**
	     * Obtains the Datasource resource name which associates the physical
	     * database connection to the database connection API implementation.
	     * 
	     * @return the datasource name
	     * @throws DatabaseException
	     *             Problem loading DB-ORM-Config, DB-ORM-Config cannot be found,
	     *             or <i>environment</i> variable is not set in application
	     *             configuration.
	     */
	    protected String getEnvDbResourceName() {
	        // Identify either the general purpose or application specific
	        // DB-ORM-Config file.
	        String ormConfigFile = null;
	        if (contextName == null) {
	            ormConfigFile ="ormConfigFile.txt";
	        }
	       
	        // Load DB-ORM-Config file
	       
	        String resourceName = "ormConfigResourceName";
	        return resourceName;
	    }

	    /**
	     * Returns the ResourceBundle instance containing ORM related configuration
	     * properties
	     * 
	     * @return the config
	     */
	    public Properties getConfig() {
	        return config;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.api.ConnectionProvider#setContextName(java.lang.String)
	     */
	    @Override
	    public void setContextName(String ctxName) {
	        this.contextName = ctxName;

	    }

	    /**
	     * @return the contextName
	     */
	    public String getContextName() {
	        return contextName;
	    }
}
