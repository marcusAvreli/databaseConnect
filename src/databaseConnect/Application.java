package databaseConnect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseConnect.api.ConnectionProvider;
import databaseConnect.api.DataSourceApi;
import databaseConnect.api.persistence.db.DatabaseConnectionBean;
import databaseConnect.api.persistence.db.DatabaseConnectionFactory;
import databaseConnect.api.persistence.db.orm.DataSourceFactory;
import databaseConnect.api.pool.DatabaseConnectionPool;
import com.mysql.cj.*;
public class Application {
	public static final Logger logger = LogManager.getLogger(Application.class);
	private static String workingDirPathName = System.getProperty("user.dir");
	public static void main(String[] args) {			
			Context.getInstance().initApp(workingDirPathName);
			
			logger.info("This is info message comming from ");
			//https://github.com/rmt2bsc/application_framework_suite/blob/631862d782c6812df90b15fdcac910317e7db8b8/fw_parent/fw_jsp/src/main/java/com/api/jsp/action/AbstractActionHandler.java#L1061
			DatabaseConnectionFactory f = new DatabaseConnectionFactory();
	        ConnectionProvider dbProvider = f.getEnvConnectionProviderApi(null);
	        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
	        pool.addConnectionApi(dbProvider);
	        System.setProperty("databaseConnect", "java:comp/env");
	        logger.info("connection pool valid:"+pool.isConnectionPoolValid());
	        // Retrieve the item so it can be deleted!
	        DatabaseConnectionBean dbConn = DatabaseConnectionPool
	                .getAvailConnectionBean();
	        DataSourceApi dso = DataSourceFactory.create(dbConn, "datasourceString");
	        dso.setDatasourceSql(1, null);
	        dso.setDatasourceSql(1, "id = " + "ggg");

	        // Create non-scrollable updateable resultset
	        dso.executeQuery(true, true);
			
	}
}
