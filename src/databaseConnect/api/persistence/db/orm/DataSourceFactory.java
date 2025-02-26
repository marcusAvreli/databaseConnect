package databaseConnect.api.persistence.db.orm;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseConnect.api.DaoApi;
import databaseConnect.api.DataSourceApi;
import databaseConnect.api.persistence.db.DatabaseConnectionBean;
import databaseConnect.api.pool.DatabaseConnectionPool;

/**
 * Factory designed to create new instances of DataSourceApi and DaoApi objects.
 * 
 * @author roy.terrell
 * 
 */
public class DataSourceFactory extends DataSourceConverter {
	private static final Logger logger = LogManager.getLogger(DataSourceFactory.class);

    /**
     * Creates a XML datasource api from NulltableView view.
     * 
     * @param _dbo
     *            {@link DatabaseConnectionBean}
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo) {
        return DataSourceFactory.create(_dbo, "NulltableView");
    }

    /**
     * Creates a XML RMT2DataSourceApi object using _dbo and the _dataSourceName
     * 
     * @param _dbo
     *            {@link DatabaseConnectionBean}
     * @param _dataSourceName
     *            The name of the datasource
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo,
            String _dataSourceName) {
    
        return null;
    }

    /**
     * Creates a SQL or XML ObjectMapperAttrib using a DatabaseConnectionBean
     * and a RMT2TagQueryBean
     * 
     * @param _dbo
     *            {@link DatabaseConnectionBean}
     * @param _queryData
     *            Object containing the details about the type of datasource to
     *            create.
     * @return {@link RMT2DataSourceApi}
     */
    public static DataSourceApi create(DatabaseConnectionBean _dbo,
            RMT2TagQueryBean _queryData) {
      return null;
    }

    /**
     * Creates a DaoApi object using the RMT2 ORM implementation.
     * 
     * @param connection
     *            an instance of {@link DatabaseConnectionBean} which contains
     *            the actual database connection.
     * @return {@link DaoApi}
     */
    public static DaoApi createDao(DatabaseConnectionBean connection) {
       return null;
       
    }

    /**
     * Creates a DaoApi object using the Hibernate ORM implementation.
     * 
     * @param connection
     *            an instance of {@link DatabaseConnectionBean} which contains
     *            the actual database connection.
     * @return null instance of {@link DaoApi}
     */
    public static DaoApi createHibernateDao(DatabaseConnectionBean connection) {
        return null;
    }

    /**
     * Creates a DaoApi object using the iBatis (MyBatis) ORM implementation.
     * 
     * @param connection
     *            an instance of {@link DatabaseConnectionBean} which contains
     *            the actual database connection.
     * @return null instance of {@link DaoApi}
     */
    public static DaoApi createIbatisDao(DatabaseConnectionBean connection) {
        return null;
    }

    /**
     * Creates a DaoApi object using a DatabaseConnectionBean and the data
     * source name.
     * 
     * @param _dbo
     *            A valid Database connection bean and cannot be null.
     * @param dsn
     *            The name of the data source.
     * @return {@link DaoApi}
     */
    public static DaoApi createDao(Object _dbo, String dsn) {
        DatabaseConnectionBean dbBean = null;
        if (_dbo != null && _dbo instanceof DatabaseConnectionBean) {
            dbBean = (DatabaseConnectionBean) _dbo;
        }
        try {
            DaoApi api = new RdbmsDaoImpl(dbBean, dsn);
            api.setConnector(dbBean);
            return api;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a SQL or XML ObjectMapperAttrib using a DatabaseConnectionBean
     * and a RMT2TagQueryBean
     * 
     * @param _dbo
     *            {@link DatabaseConnectionBean}
     * @param _queryData
     *            Object containing the details about the type of datasource to
     *            create.
     * @return {@link RMT2DataSourceApi}
     */
    public static DaoApi createDao(DatabaseConnectionBean _dbo,
            RMT2TagQueryBean _queryData) {
        try {
            logger.log(Level.DEBUG, "Begin to Create DAO object");
        
            return api;
        } catch (Exception e) {
            return null;
        }
    }
}