package com.warehouse.dao;

import com.warehouse.Constants;
import com.warehouse.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Client data access object implementation
 */
@SuppressWarnings("Duplicates")
public class ClientDAO extends AbstractDAO<Client> {

    // ********* SQL STATEMENTS/QUERIES FOR CLIENT TABLE ******** //
    private static final String QUERY_GET_PAGE = "SELECT * FROM warehouse.client ORDER BY warehouse.client.personal_code LIMIT " + Constants.PAGE_SIZE + " OFFSET ?";

    private static final String QUERY_COUNT = "SELECT count(*) FROM warehouse.client";

    private static final String QUERY_GET_BY_PERSONAL_CODE = "SELECT * FROM warehouse.client WHERE warehouse.client.personal_code = ?";

    private static final String STATEMENT_INSERT = "INSERT INTO warehouse.client (personal_code, first_name, last_name, phone, email, birth_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String STATEMENT_UPDATE = "UPDATE warehouse.client SET personal_code = ?, first_name = ?, last_name = ?, phone = ?, email = ?, birth_date = ? WHERE warehouse.client.personal_code = ?";

    private static final String STATEMENT_DELETE = "DELETE FROM warehouse.client WHERE warehouse.client.personal_code = ?";
    // ***** END OF SQL STATEMENTS/QUERIES FOR CLIENT TABLE ***** //

    /**
     * Default class constructor
     */
    public ClientDAO() {
        super();

        super.QUERY_COUNT = this.QUERY_COUNT;
        super.QUERY_GET_PAGE = this.QUERY_GET_PAGE;
    }

    /**
     * Saves a single object
     *
     * @param client object to save
     * @return saved object
     */
    @Override
    public Client save(Client client) {
        database.connect();
        database.prepareStatement(STATEMENT_INSERT);

        try {
            PreparedStatement statement = database.getStatement();

            setParameters(statement, client);

            statement.execute();

            database.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return getByPersonalCode(client.getPersonalCode());
    }

    /**
     * Updates a single object
     *
     * @param oldPersonalCode
     * @param client object to update
     *
     * @return updated object
     */
    public Client update(Long oldPersonalCode, Client client) {
        database.connect();
        database.prepareStatement(STATEMENT_UPDATE);

        try {
            PreparedStatement statement = database.getStatement();

            setParameters(statement, client);

            statement.setLong(7, oldPersonalCode);

            statement.execute();

            database.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return getByPersonalCode(client.getPersonalCode());
    }

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    @Override
    public Client delete(Long id) {
        Client old = getByPersonalCode(id);

        if (old == null) {
            return null;
        }

        database.connect();
        database.prepareStatement(STATEMENT_DELETE);

        try {
            PreparedStatement s = database.getStatement();

            s.setLong(1, id);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();

        return old;
    }

    /**
     * Sets insert/update parameters
     *
     * @param statement
     * @param client
     */
    private void setParameters(PreparedStatement statement, Client client) {
        try {
            statement.setLong(1, client.getPersonalCode());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getLastName());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getEmail());
            statement.setDate(6, java.sql.Date.valueOf(client.getBirthDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
        } catch (SQLException ex) {
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
    protected List<Client> extractResultSet(ResultSet result) {
        List<Client> clients = new ArrayList<>();

        try {
            while (result.next()) {
                clients.add(new Client(
                        result.getLong("personal_code"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("phone"),
                        result.getString("email"),
                        result.getDate("birth_date")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return clients;
    }

    /**
     * Gets a single  client by personal code
     *
     * @param personalCode
     * @return
     */
    public Client getByPersonalCode(long personalCode) {
       try {
            database.connect();
            database.prepareStatement(QUERY_GET_BY_PERSONAL_CODE);

            ResultSet results = database.executeQuery(-1, personalCode);

            results.first();

            Client client = new Client(
                    results.getLong("personal_code"),
                    results.getString("first_name"),
                    results.getString("last_name"),
                    results.getString("phone"),
                    results.getString("email"),
                    results.getDate("birth_date")
            );

            database.disconnect();

            return client;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
