package com.warehouse.dao;

import com.warehouse.Constants;
import com.warehouse.model.Client;
import com.warehouse.model.StoredItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Stored item data access object implementation
 */
@SuppressWarnings("Duplicates")
public class StoredItemDAO extends AbstractDAO<StoredItem> {

    // ********* SQL STATEMENTS/QUERIES FOR CLIENT TABLE ******** //
    private static final String QUERY_GET_PAGE = "SELECT warehouse.stored_item.count, warehouse.stored_item.fk_storage_area, warehouse.stored_item.id_stored_item, warehouse.item.name, warehouse.item.description, warehouse.item.width, warehouse.item.height, warehouse.item.length, warehouse.item.weight, warehouse.client.first_name, warehouse.client.last_name, warehouse.warehouse.address, warehouse.warehouse.fk_location FROM warehouse.stored_item INNER JOIN warehouse.item ON warehouse.stored_item.fk_property = warehouse.item.id INNER JOIN warehouse.client ON warehouse.stored_item.fk_owner = warehouse.client.personal_code INNER JOIN warehouse.warehouse ON (SELECT warehouse.rentable_area.fk_building FROM warehouse.rentable_area WHERE warehouse.rentable_area.id = fk_storage_area) = warehouse.warehouse.id_warehouse LIMIT " + Constants.PAGE_SIZE + " OFFSET ?";

    private static final String QUERY_COUNT = "SELECT count(*) FROM warehouse.stored_item";

    private static final String QUERY_GET_CATEGORIES = "SELECT warehouse.item_category.name FROM warehouse.item_category";

    private static final String QUERY_GET_OWNERS = "SELECT warehouse.client.first_name, warehouse.client.last_name, warehouse.client.personal_code FROM warehouse.client";

    private static final String QUERY_GET_AREAS = "SELECT warehouse.rentable_area.id, warehouse.warehouse.address, warehouse.warehouse.fk_location FROM warehouse.rentable_area INNER JOIN warehouse.warehouse ON warehouse.rentable_area.fk_building = warehouse.warehouse.id_warehouse";

    private static final String STATEMENT_INSERT = "INSERT INTO warehouse.item (id, name, description, width, length, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_CATEGORY_ID = "SELECT warehouse.item_category.id_item_category FROM warehouse.item_category WHERE warehouse.item_category.name = ?";

    private static final String INSERT_CATEGORY = "INSERT INTO warehouse.belongs_to (fk_category, fk_property) VALUES (?, ?)";

    private static final String INSERT_STORED_ITEM = "INSERT INTO warehouse.stored_item (count, id_stored_item, fk_property, fk_storage_area, fk_owner) VALUES (?, ?, ?, ?, ?)";

    private static final String DELETE = "DELETE FROM warehouse.stored_item WHERE warehouse.stored_item.id_stored_item = ?";
    // ***** END OF SQL STATEMENTS/QUERIES FOR CLIENT TABLE ***** //

    /**
     * Default class constructor
     */
    public StoredItemDAO() {
        super();

        super.QUERY_GET_PAGE = this.QUERY_GET_PAGE;
        super.QUERY_COUNT = this.QUERY_COUNT;
    }

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    @Override
    public StoredItem delete(Long id) {
        database.connect();

        try {
            database.prepareStatement(DELETE);

            PreparedStatement s = database.getStatement();

            s.setLong(1, id);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            database.disconnect();
            return null;
        }

        database.disconnect();

        return new StoredItem();
    }

    /**
     * Saves a new item
     * @param item
     * @return true on success
     */
    public boolean saveItem(StoredItem item) {
        try {
            database.connect();
            database.prepareStatement(STATEMENT_INSERT);

            PreparedStatement s = database.getStatement();

            s.setLong(1, item.getId());
            s.setString(2, item.getName());
            s.setString(3, item.getDescription());
            s.setDouble(4, item.getWidth());
            s.setDouble(5, item.getLength());
            s.setDouble(6, item.getHeight());
            s.setDouble(7, item.getWeight());

            s.execute();

            database.prepareStatement(INSERT_CATEGORY);

            s = database.getStatement();

            long catId = getCategoryId(item.getCategory());

            s.setLong(1, catId);
            s.setLong(2, item.getId());

            s.execute();

            database.disconnect();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Gets item category
     * @param category
     * @return
     */
    private long getCategoryId(String category) {
        long id = 0;

        try {
            database.connect();
            database.prepareStatement(GET_CATEGORY_ID);

            PreparedStatement s = database.getStatement();

            s.setString(1, category);

            ResultSet r = s.executeQuery();

            r.first();

            id = r.getLong("id_item_category");

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Adds a new stored item
     *
     * @param id
     * @param count
     * @param owner
     * @param area
     */
    public void saveStoredItem(Long id, int count, String owner, String area) {
        String[] data = owner.split(" ");

        Long ownerId = Long.parseLong(data[data.length - 1]);
        Long areaId = Long.parseLong(area.split(",")[0]);

        try {
            database.connect();
            database.prepareStatement(INSERT_STORED_ITEM);

            PreparedStatement s = database.getStatement();

            s.setInt(1, count);
            s.setLong(2, System.currentTimeMillis());
            s.setLong(3, id);
            s.setLong(4, areaId);
            s.setLong(5, ownerId);

            s.execute();

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts a result set into a list
     *
     * @param result result set
     * @return list with records
     */
    @Override
    protected List<StoredItem> extractResultSet(ResultSet result) {
        List<StoredItem> items = new ArrayList<>();

        try {
            while (result.next()) {
                items.add(new StoredItem(
                        result.getLong("id_stored_item"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getDouble("width") + "x" + result.getDouble("length") + "x" + result.getDouble("height"),
                        0,
                        0,
                        0,
                        result.getDouble("weight"),
                        null,
                        result.getInt("count"),
                        new Client(
                                0L,
                                result.getString("first_name"),
                                result.getString("last_name"),
                                null, null, null
                        ),
                        result.getString("address") + ", " + result.getString("fk_location")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return items;
    }

    /**
     * Gets all item categories
     * @return
     */
    public List<String> getCategories() {
        database.connect();
        database.prepareStatement(QUERY_GET_CATEGORIES);

        List<String> categories = new ArrayList<>();

        try {
            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                categories.add(r.getString("name"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * Gets all possible item owners
     *
     * @return
     */
    public List<String> getOwners() {
        database.connect();
        database.prepareStatement(QUERY_GET_OWNERS);

        List<String> owners = new ArrayList<>();

        try {
            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                owners.add(r.getString("first_name") + " " + r.getString("last_name") + " " + r.getLong("personal_code"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return owners;
    }

    /**
     * Gets all possible storage areas
     * @return
     */
    public List<String> getAreas() {
        database.connect();
        database.prepareStatement(QUERY_GET_AREAS);

        List<String> areas = new ArrayList<>();

        try {
            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                areas.add(r.getLong("id") + ", " + r.getString("address") + ", " + r.getString("fk_location"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return areas;
    }
}
