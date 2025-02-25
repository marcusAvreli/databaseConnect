package databaseConnect.api.persistence.db;

public class DatabaseConnectionConstants {
	  public static final String CONNECTION_CONFIG = "config/DB-ORM-Config.properties";

	    public static final String API_CONNECTION_PLACEHOLDER = "${ApiName}";

	    public static final String API_CONNECTION_CONFIG = "config.${ApiName}-DB-ORM-Config";

	    /**
	     * Create DatabaseConnectionConstants object
	     */
	    public DatabaseConnectionConstants() {
	        return;
	    }
}
