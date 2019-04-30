package com.warehouse.dao;

import java.util.List;

/**
 * Data access object abstraction
 *
 * @param <T> type of the accessed object
 */
public interface DAO<T> {

    /**
     * Gets all objects
     *
     * @return list of all objects
     */
    List<T> getAll();

    /**
     * Gets a number of objects
     *
     * @param page page number
     *
     * @return list of objects
     */
    List<T> getPage(Integer page);

    /**
     * Saves a single object
     *
     * @param object object to save
     * @return saved object
     */
    T save(T object);

    /**
     * Updates a single object
     *
     * @param object object to update
     * @return updated object
     */
    T update(T object);

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    T delete(Long id);

    /**
     * Gets the total number of records
     * @return
     */
    int count();
}
