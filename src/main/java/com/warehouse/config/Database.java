package com.warehouse.config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Database {

    /**
     * Connects to the database
     */
    void connect();

    /**
     * Disconnects from the database
     */
    void disconnect();

    /**
     * Prepares an sql statement
     *
     * @param statement statement to prepare
     */
    void prepareStatement(String statement);

    /**
     * Executes a prepared query
     *
     * @param offset result limit
     * @param params params for the query
     * @return query results
     */
    ResultSet executeQuery(int offset, String... params);

    /**
     * Executes a prepared query
     *
     * @param offset result limit
     * @param params params for the query
     * @return query results
     */
    ResultSet executeQuery(int offset, Long... params);

    /**
     * Executes a prepared statement
     *
     * @param params params for the statement
     *
     * @return number of rows affected, -1 if it was a query
     */
    int executeStatement(String... params);

    /**
     * Gets the prepared statement
     * @return
     */
    PreparedStatement getStatement();
}
