package databaseConnect.api.persistence.db;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseConnect.Context;
import databaseConnect.api.CannotConnectException;
import databaseConnect.api.ConnectionProvider;
import databaseConnect.api.persistence.DatabaseException;
import databaseConnect.api.util.RMT2BeanUtility;
import databaseConnect.com.RMT2Base;

public class DatabaseConnectionFactory extends RMT2Base {

	private static final Logger logger = LogManager.getLogger(DatabaseConnectionFactory.class);

    /**
     * This is the name of the implementing class of interface
     * {@link ConnectionProvider} which provides the core functionality of
     * creating the {@link DatabaseConnectionBean}.
     * <p>
     * The value must be a fully qualified class name in order for this factory
     * work properly.
     */
    public static final String CONNECTION_PROVIDER_CLASS = "DbConnectionProviderClass";

    /**
     * Default constructor.
     */
    public DatabaseConnectionFactory() {
        super();
        logger.info(DatabaseConnectionFactory.class.getName()
                + " is initialized");
        return;
    }

    /**
     * Creates an instance of <i>ConnectinProvider</i> that is appropriate for
     * the environment the application is executed.
     * <p>
     * The System environment variable, <i>env</i>, must be set to either
     * "test", "dev", or "prod" in order to successfully obtain the correct
     * implementation for the connection provider API.
     * 
     * @param contextName
     *            The context name used to target a particular DB properties
     *            configuration. When equal to null, DB-ORM-Config.properties is
     *            targeted. When a value other than null,
     *            &lt;<i>contextName</i>&gt;-DB-ORM-Config.properties is
     *            targeted.
     * @return an instance of {@link ConnectionProvider}
     * @throws DatabaseException
     *             the System environment variable, <i>env</i>, is not set or an
     *             inapproriate implementation class name was specified.
     * 
     */
    public ConnectionProvider getEnvConnectionProviderApi(String contextName)
            throws DatabaseException {
        String env = "Dev";
        /*
         if (env == null) {
            this.msg = "Error obtaining connection provider implementing class.  System property, \"env\", is not set.  Unable to determine the environment of application (Test, Production, or Development)";
            logger.fatal(this.msg);
            throw new DatabaseException(this.msg);
        }
        */
        String ormConfigFile = null;
        if (contextName == null) {
            ormConfigFile = DatabaseConnectionConstants.CONNECTION_CONFIG;
        }
        else {
            ormConfigFile ="ormConfigFile";
                    
        }
        //ResourceBundle rb = RMT2File.loadAppConfigProperties(ormConfigFile);
        Properties props = Context.getInstance().initSettings(ormConfigFile); 
        
        String implClassKey = env.toLowerCase() + "."
                + DatabaseConnectionFactory.CONNECTION_PROVIDER_CLASS;
       
        logger.info("The selected database connectin provider implementation is, "
                + implClassKey);
        String className = props.getProperty(implClassKey);

        // The implementation of ConnectionProvider should be able to initialize
        // itself when instantiated.
        ConnectionProvider api = null;
        try {
            RMT2BeanUtility beanUtil = new RMT2BeanUtility();
            api = (ConnectionProvider) beanUtil.createBean(className);
        } catch (Exception e) {
            this.msg = "Unable to create the database ConnectionProvider API based on the implementation class specified: "
                    + className;
            throw new DatabaseException(this.msg, e);
        }
        return api;
    }

    /**
     * Obtains a provider specific database connection packaged as a
     * DatabaseConnectionBean instance.
     * <p>
     * In order to obtain a database connection successfully, this method
     * requires the fully qualified name of the class that implements the
     * interface, {@link ConnectionProvider}, to be specified in
     * DB-ORM-Config.properties. The class name will be mapped by the key,
     * "DbconnectionProviderClass".
     * 
     * @return {@link bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @throws DatabaseException
     *             database access error
     */
    public DatabaseConnectionBean getProviderConnection() throws DatabaseException {
        // The implementation of ConnectionProvider should be able to initialize
        // itself when instantiated.
        ConnectionProvider api = this.getEnvConnectionProviderApi(null);

        try {
            return (DatabaseConnectionBean) api.getConnection();
        } catch (CannotConnectException e) {
            this.msg = "Unable to obtain connection for source specified in DB-ORM-Config.properties";
            throw new DatabaseException(this.msg, e);
        }
    }

    /**
     * Obtains a provider specific database connection packaged as a
     * DatabaseConnectionBean instance.
     * <p>
     * This factory method intructs the DAO to target a specific database
     * configuration included in DB-ORM-Config.properites by specifying
     * <i>contextName</i>.
     * <p>
     * In order to obtain a database connection successfully, this method
     * requires the fully qualified name of the class that implements the
     * interface, {@link ConnectionProvider}, to be specified in
     * DB-ORM-Config.properties. The class name will be mapped by the key,
     * "DbconnectionProviderClass".
     * 
     * @param contextName
     *            this parameter points to the name of the class that will be
     *            created as an instance of {@link ConnectionProvider}
     * @return
     * @throws DatabaseException
     */
    public DatabaseConnectionBean getProviderConnection(String contextName) throws DatabaseException {
        // The implementation of ConnectionProvider should be able to initialize
        // itself when instantiated.
        ConnectionProvider api = this.getEnvConnectionProviderApi(contextName);
        try {
            // Set context name to target a particular database configuration.
            api.setContextName(contextName);
            return (DatabaseConnectionBean) api.getConnection();
        } catch (CannotConnectException e) {
            msg = "Unable to obtain connection for source specified in DB-ORM-Config.properties";
            throw new DatabaseException(msg, e);
        }
    }

    /**
     * Obtains a provider specific database connection packaged as a
     * DatabaseConnectionBean instance.
     * 
     * @param dbUrl
     *            the database URL
     * @param uid
     *            the database user id
     * @param pw
     *            the database password
     * @return an instance of {@link DatabaseConnectionBean}
     * @throws DatabaseException
     */
    public DatabaseConnectionBean getSimpleConnection(String dbUrl,
            String uid, String pw) throws DatabaseException {
        String msg = null;
        ConnectionProvider api = null;
        try {
            api = null;
            return (DatabaseConnectionBean) api.getConnection();
        } catch (Exception e) {
            msg = "Error establishing a database connection via simple means: URL="
                    + dbUrl + ", User Id=" + uid + ", Password=" + pw;
            throw new DatabaseException(msg, e);
        }
    }
}
