package databaseConnect.api.persistence.db.orm.query;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.SystemException;

import databaseConnect.Application;
import databaseConnect.api.ProductBuilder;
import databaseConnect.api.ProductBuilderException;
import databaseConnect.api.persistence.db.orm.OrmBean;
import databaseConnect.api.persistence.db.orm.bean.DataSourceColumn;

/**
 * This class provides complex processes to construct and deconstruct SQL order
 * by clause as {@link com.api.Product Product}.
 * 
 * @author RTerrell
 * 
 */
class OrmSqlOrdering extends AbstractOrmQuery implements ProductBuilder {
    private static final long serialVersionUID = 1874037456487871309L;

    private  Logger logger = null;

    private OrmBean pojo;

    /**
     * Constructs a OrmSqlOrdering with DaoApi data source and the tartget POJO
     * object.
     * 
     * @param src
     *            {@link com.api.DaoApi DaoApi}
     * @param pojo
     *            {@link bean.OrmBean OrmBean}
     * @throws SystemException
     */
    public OrmSqlOrdering(Object src, OrmBean pojo) throws SystemException {
        super(src);
        this.pojo = pojo;
        this.logger = LogManager.getLogger(OrmSqlOrdering.class);
    }

    /**
     * Constructs a SQL order by clause from an ORM data source.
     * 
     * @throws ProductBuilderException
     */
    public void assemble() throws ProductBuilderException {
        this.logger.log(Level.DEBUG, "Assembling ORM Order Criteria");
        StringBuffer sql = new StringBuffer(100);
        String direction = null;
        String prop = null;
        String result = null;
        Iterator criteriaEnum = null;
        Hashtable colDefs = null;
        DataSourceColumn colBean = null;

        // No need to continue if order criteria is not available
        if (!this.pojo.isOrderByAvailable()) {
            this.setQueryString(null);
            this.setQueryComp(null);
            return;
        }

        // Get selection criteria property keys
        criteriaEnum = this.pojo.getOrderBy().keySet().iterator();
        // Get DataSource Column bean objects
        colDefs = this.dsAttr.getColumnDef();

        while (criteriaEnum.hasNext()) {
            prop = (String) criteriaEnum.next();
            if (prop == null) {
                continue;
            }
            // Use property to get corresponding DB name
            colBean = (DataSourceColumn) colDefs.get(prop);
            if (colBean == null) {
                continue;
            }

            // Build criteria for current DB Column
            direction = this.pojo.getOrderBy(colBean.getName());
            if (colBean != null) {
                if (sql.length() > 0) {
                    sql.append(", ");
                }
                sql.append(colBean.getDbName());
                if (direction == null) {
                    direction = " asc ";
                }
                sql.append(" ");
                sql.append(direction);

            }
        } // end while

        result = sql.toString();

        this.setQueryString(result);
        this.setQueryComp(this.dsAttr);
    }

    /**
     * No implementation for disassebling an SQL order by clause.
     */
    public void disAssemble() throws ProductBuilderException {
        return;
    }

}