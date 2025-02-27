/**
 * 
 */
/**
 * 
 */
module databaseConnect {
	
		requires java.scripting;
		requires slf4j.api;
		requires org.apache.logging.log4j.core;
		requires org.apache.logging.log4j;
		requires java.sql;
		requires java.naming;
		requires java.desktop;
		requires java.xml;
		requires java.activation;
		requires mysql.connector.j;
		exports databaseConnect.api.persistence.db;
		
	
}
