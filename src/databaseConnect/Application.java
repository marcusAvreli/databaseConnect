package databaseConnect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseConnect.api.ConnectionProvider;
import databaseConnect.api.persistence.db.DatabaseConnectionFactory;
import databaseConnect.api.pool.DatabaseConnectionPool;

public class Application {
	public static final Logger logger = LogManager.getLogger(Application.class);
	private static String workingDirPathName = System.getProperty("user.dir");
	public static void main(String[] args) {			
			Context.getInstance().initApp(workingDirPathName);
			
			logger.info("This is info message comming from ");
			
			DatabaseConnectionFactory f = new DatabaseConnectionFactory();
	        ConnectionProvider dbProvider = f.getEnvConnectionProviderApi(null);
	        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
	        pool.addConnectionApi(dbProvider);
	        logger.info("connection pool valid:"+pool.isConnectionPoolValid());
			
	}
}
