package databaseConnect.api;

import databaseConnect.api.persistence.DatabaseException;

public interface ConnectionProvider {

    /**
     * Get the next available connection from the collecitve of connections.
     * 
     * @return The connection.
     * @throws DatabaseException
     * @throws CannotConnectException
     */
    Object getConnection() throws CannotConnectException, DatabaseException;

    /**
     * Set the application context for this connection provider.
     * 
     * @param ctxName
     *            the name of the context
     */
    void setContextName(String ctxName);

    /**
     * Get the application context for this connection provider.
     * 
     * @return the context name
     */
    String getContextName();
} // End of Class
