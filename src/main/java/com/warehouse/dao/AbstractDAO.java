package com.warehouse.dao;

import com.warehouse.Constants;
import com.warehouse.config.Database;
import com.warehouse.config.PSQLDatabase;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.util.List;

/**
 * Abstract class that implements data access object interface
 * @param <T>
 */
public abstract class AbstractDAO<T> implements DAO<T> {

    // used to connect to the database
    protected Database database;

    // query used to count all rows
    protected String QUERY_COUNT;

    // query used to get a single page
    protected String QUERY_GET_PAGE;

    /**
     * Default class constructor
     */
    protected AbstractDAO() {
        database = new PSQLDatabase();
    }

    /**
     * Gets all objects
     *
     * @return list of all objects
     */
    @Override
    public List<T> getAll() {
        throw new NotImplementedException();
    }

    /**
     * Gets a number of objects
     *
     * @param page page number
     * @return list of objects
     */
    @Override
    public List<T> getPage(Integer page) {
        if (QUERY_GET_PAGE != null) {
            database.connect();
            database.prepareStatement(QUERY_GET_PAGE);

            ResultSet result = database.executeQuery((page - 1) * Constants.PAGE_SIZE, new String[] {});

            List<T> clients = extractResultSet(result);

            database.disconnect();

            return clients;
        }

        throw new NotImplementedException();
    }

    /**
     * Converts a result set into a list
     *
     * @param result result set
     * @return list with records
     */
    protected abstract List<T> extractResultSet(ResultSet result);

    /**
     * Saves a single object
     *
     * @param object object to save
     * @return saved object
     */
    @Override
    public T save(T object) {
        throw new NotImplementedException();
    }

    /**
     * Updates a single object
     *
     * @param object object to update
     * @return updated object
     */
    @Override
    public T update(T object) {
        throw new NotImplementedException();
    }

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    @Override
    public T delete(Long id) {
        throw new NotImplementedException();
    }

    /**
     * Gets the total number of records
     *
     * @return
     */
    @Override
    public int count() {
        if (QUERY_COUNT != null) {
            database.connect();
            database.prepareStatement(QUERY_COUNT);

            ResultSet result = database.executeQuery(-1, new String[] {});

            int count = 0;

            try {
                result.next();
                count = result.getInt("COUNT");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            database.disconnect();

            return count;
        }

        throw new NotImplementedException();
    }
}
