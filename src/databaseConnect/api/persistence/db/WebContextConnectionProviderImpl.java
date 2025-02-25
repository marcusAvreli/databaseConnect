package databaseConnect.api.persistence.db;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.SystemException;

import databaseConnect.api.CannotConnectException;
import databaseConnect.api.persistence.CannotReadManagerConfigurationException;
import databaseConnect.api.persistence.DatabaseException;

public class WebContextConnectionProviderImpl extends AbstractConnectionProviderImpl {

	public static final Logger logger = LogManager.getLogger(WebContextConnectionProviderImpl.class);

/**
* Default constructor
*/
public WebContextConnectionProviderImpl() {
super();
// this.ctxName = null;
logger.info(WebContextConnectionProviderImpl.class.getName()
        + " is initialized");
}

/**
* Gets the next available database connection bean object.
* <p>
* By default, the connection will be set to manage its transactions in an
* Auto Commit fashion.
* 
* @return {@link bean.db.DatabaseConnectionBean DatabaseConnectionBean}
*         disguised as an Object.
* @throws DatabaseException
*             Problem obtaining database connection bean.
*/
public synchronized DatabaseConnectionBean getConnection()
    throws CannotConnectException, DatabaseException {
DatabaseConnectionBean dbConn = null;
try {
    dbConn = new DatabaseConnectionBean();
    // Initialize to properties of dbConn and add to the DBConnection
    // Collective
    dbConn.setNativeConnection(this.createConnection());
    // Initialize name property of dbConn. Format will be <app name> +
    // <date long value> or <connection name> + <date long value>.
    String seq = String.valueOf(new Date().getTime());
    String name = (seq);
    dbConn.setName(name);
    return dbConn;
} catch (Exception e) {
    this.msg = "Problem discovered during the process of creating and initializing database connection bean";
    throw new CannotReadManagerConfigurationException(this.msg, e);
}
}

/**
* Create a Connection and return to caller.
* 
* @return Connection
* @throws SystemException
*             Error establishing the database connection.
*/
private synchronized Connection createConnection()
    throws CannotConnectException, SystemException {
// Get specific JNDI Datasource name which is mapped to "DbResourceName"
String resourceName = this.getEnvDbResourceName();
Connection con = null;
try {
    // Obtain JDBC Connection via DataSource
    Context ctx;
    ctx = new InitialContext();
    Context env = (Context) ctx.lookup("java:comp/env");
    DataSource jdbcDs = (DataSource) env.lookup(resourceName);
    if (jdbcDs == null) {
        this.msg = "JNDI lookup return invalid JDBC DataSource reference";
        throw new CannotConnectException(this.msg);
    }
    con = jdbcDs.getConnection();
    if (con == null) {
        this.msg = "Unable to obtain JDBC connection via DataSource JNDI configuration";
        throw new CannotConnectException(this.msg);
    }
    // IS-41: Turn of the auto commit feature for every connection
    // obtained.
    con.setAutoCommit(false);
    return con;
} catch (SQLException e) {
    this.msg = "Database Server is down! - " + e.getMessage();
    throw new CannotConnectException(this.msg);
} catch (NamingException e) {
    this.msg = e.getMessage();
    throw new SystemException(this.msg);
}
}

} // End of Class