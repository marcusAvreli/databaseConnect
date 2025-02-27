package databaseConnect.api.persistence.db.orm.query;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseConnect.api.DataSourceApi;
import databaseConnect.api.Product;
import databaseConnect.api.ProductBuilder;
import databaseConnect.api.ProductBuilderException;
import databaseConnect.api.ProductDirector;
import databaseConnect.api.persistence.db.orm.OrmBean;
import databaseConnect.api.persistence.db.orm.bean.DataSourceColumn;
import databaseConnect.api.persistence.db.orm.bean.TableUsageBean;
import databaseConnect.com.SystemException;

/**
 * This class provides complex processes to construct and deconstruct SQL update
 * statements as {@link com.api.Product Product}.
 * 
 * @author RTerrell
 * 
 */
class OrmSqlUpdateQuery extends AbstractOrmQuery implements ProductBuilder {
    private static final long serialVersionUID = 1874037456487871309L;

    private  Logger logger = null;

    private OrmBean pojo;

    /**
     * Constructs a OrmSqlUpdateQuery with DaoApi data source and the tartget
     * POJO object.
     * 
     * @param src
     *            {@link com.api.DaoApi DaoApi}
     * @param pojo
     *            {@link bean.OrmBean OrmBean}
     * @throws SystemException
     */
    public OrmSqlUpdateQuery(Object src, OrmBean pojo) throws SystemException {
        super(src);
        this.pojo = pojo;
        this.logger = LogManager.getLogger(OrmSqlUpdateQuery.class);
    }

    /**
     * Constructs a SQL update statement from an ORM data source.
     * 
     * @throws ProductBuilderException
     */
    public void assemble() throws ProductBuilderException {
        this.logger.log(Level.INFO, "Assembling ORM Update Statement");
        StringBuffer updateSql = new StringBuffer(100);
        StringBuffer colSql = new StringBuffer(100);
        String sql = null;
        Hashtable tables = null;
        Enumeration tempEnum = null;
        TableUsageBean tableBean = null;
        DataSourceColumn colBean = null;
        int cnt = 0;

        // Get Table data
        tables = this.dsAttr.getTables();
        tempEnum = tables.elements();
        if (tempEnum.hasMoreElements()) {
            tableBean = (TableUsageBean) tempEnum.nextElement();
            updateSql.append("Update ");
            updateSql.append(tableBean.getDbName());
            updateSql.append(" set ");
        }

        // Cycle through properties to build "set" column list.
        tempEnum = this.dsAttr.getColumnDef().elements();
        while (tempEnum.hasMoreElements()) {
            colBean = (DataSourceColumn) tempEnum.nextElement();

            // Do not want to include primary key in set clause
            if (colBean.isPrimaryKey()) {
                continue;
            }

            // Check if user intended for column to be set as null.
            if (pojo.isNull(colBean.getName())) {
                colBean.setDataValue(OrmBean.DB_NULL);
            }

            // Setup set clause
            if (cnt++ > 0) {
                colSql.append(", ");
            }
            colSql.append(colBean.getDbName());
            colSql.append(" = ");
           // colSql.append(((DataSourceApi) this.getSrc())
             //       .getSqlColumnValue(colBean));
        } // end while

        // Get where clause
        String whereSql;
        try {
            ProductBuilder builder = OrmQueryBuilderFactory.getCriteriaQuery(
                    this.getSrc(), this.pojo);
            Product sqlObj = ProductDirector.construct(builder);
            whereSql = sqlObj.toString();
        } catch (ProductBuilderException e) {
            whereSql = null;
        }

        // String together the three parts of the where statement.
        sql = updateSql.toString() + colSql.toString()
                + (whereSql == null ? "" : " where " + whereSql);

        this.setQueryString(sql);
        this.setQueryComp(this.dsAttr);
    }

    /**
     * No implementation for disassebling an SQL update.
     */
    public void disAssemble() throws ProductBuilderException {
        return;
    }

}