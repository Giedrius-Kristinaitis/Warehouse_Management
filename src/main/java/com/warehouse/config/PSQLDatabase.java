package com.warehouse.config;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to execute sql statements and queries
 */
public class PSQLDatabase implements Database {

    // used to get database connections
    private DataSource source;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    /**
     * Default class constructor. Gets data source from the JNDI
     */
    public PSQLDatabase() {
        // get the data source
        try {
            InitialContext cxt = new InitialContext();

            if (cxt == null) {
                throw new Exception("Context missing");
            }

            source = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");

            if (source == null) {
                throw new Exception("Data source not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Connects to the database
     */
    @Override
    public void connect() {
        try {
            connection = source.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Disconnects from the database
     */
    @Override
    public void disconnect() {
        if (connection != null) {
            try { connection.close(); } catch(SQLException ex) {}
            connection = null;
        }

        if (statement != null) {
            try { statement.close(); } catch(SQLException ex) {}
            statement = null;
        }

        if (resultSet != null) {
            try { resultSet.close(); } catch(SQLException ex) {}
            resultSet = null;
        }
    }

    /**
     * Prepares an sql statement
     *
     * @param statement statement to prepare
     */
    @Override
    public void prepareStatement(String statement) {
        if (connection == null) {
            throw new IllegalStateException("Not connected to the database");
        }

        try {
            this.statement = connection.prepareStatement(statement, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Executes a prepared query
     *
     * @param offset record offset
     * @param params params for the query
     * @return query results
     */
    @Override
    public ResultSet executeQuery(int offset, String... params) {
        if (connection == null) {
            throw new IllegalStateException("Not connected to the database");
        }

        if (statement == null) {
            throw new IllegalStateException("Query not prepared");
        }

        try {
            if (offset >= 0) {
                statement.setLong(1, offset);
            }

            if (params != null) {
                int index = offset >= 0 ? 1 : 0;

                for (String param: params) {
                    statement.setString(++index, param);
                }
            }

            return statement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Executes a prepared query
     *
     * @param offset result limit
     * @param params params for the query
     * @return query results
     */
    @Override
    public ResultSet executeQuery(int offset, Long... params) {
        if (connection == null) {
            throw new IllegalStateException("Not connected to the database");
        }

        if (statement == null) {
            throw new IllegalStateException("Query not prepared");
        }

        try {
            if (offset >= 0) {
                statement.setLong(1, offset);
            }

            if (params != null) {
                int index = offset >= 0 ? 1 : 0;

                for (Long param: params) {
                    statement.setLong(++index, param);
                }
            }

            return statement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Executes a prepared statement
     *
     * @param params params for the statement
     *
     * @return number of rows affected, -1 if it was a query
     */
    @Override
    public int executeStatement(String... params) {
        if (connection == null) {
            throw new IllegalStateException("Not connected to the database");
        }

        if (statement == null) {
            throw new IllegalStateException("Statement not prepared");
        }

        try {
            if (params != null) {
                int index = 0;

                for (String param: params) {
                    statement.setString(++index, param);
                }
            }

            if (!statement.execute()) {
                return statement.getUpdateCount();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /**
     * Gets the prepared statement
     *
     * @return
     */
    @Override
    public PreparedStatement getStatement() {
        if (statement == null) {
            throw new IllegalStateException("Statement not prepared");
        }

        return statement;
    }
}
