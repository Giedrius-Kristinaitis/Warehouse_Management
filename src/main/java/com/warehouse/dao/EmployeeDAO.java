package com.warehouse.dao;

import com.warehouse.Constants;
import com.warehouse.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee data access object implementation
 */
@SuppressWarnings("Duplicates")
public class EmployeeDAO extends AbstractDAO<Employee> {

    // ********* SQL STATEMENTS/QUERIES FOR EMPLOYEE TABLE ******** //
    private static final String QUERY_GET_PAGE = "SELECT warehouse.employee.time_card_number, warehouse.employee.first_name, warehouse.employee.last_name, warehouse.authorities.name as authority, warehouse.warehouse.address as workplace, warehouse.warehouse.fk_location as city FROM warehouse.employee INNER JOIN warehouse.authorities ON warehouse.employee.authority = warehouse.authorities.id_authorities INNER JOIN warehouse.warehouse ON warehouse.employee.fk_workplace = warehouse.warehouse.id_warehouse ORDER BY warehouse.employee.time_card_number LIMIT " + Constants.PAGE_SIZE + " OFFSET ?";

    private static final String QUERY_COUNT = "SELECT count(*) FROM warehouse.employee";

    private static final String QUERY_GET_BY_NUMBER = "SELECT warehouse.employee.time_card_number, warehouse.employee.first_name, warehouse.employee.last_name, warehouse.authorities.name as authority, warehouse.warehouse.address as workplace, warehouse.warehouse.fk_location as city FROM warehouse.employee INNER JOIN warehouse.authorities ON warehouse.employee.authority = warehouse.authorities.id_authorities INNER JOIN warehouse.warehouse ON warehouse.employee.fk_workplace = warehouse.warehouse.id_warehouse WHERE warehouse.employee.time_card_number = ?";

    private static final String QUERY_GET_AUTHORITIES = "SELECT * FROM warehouse.authorities";

    private static final String QUERY_GET_WORKPLACES = "SELECT warehouse.warehouse.address as workplace, warehouse.warehouse.fk_location as city FROM warehouse.warehouse";

    private static final String STATEMENT_INSERT = "INSERT INTO warehouse.employee (time_card_number, first_name, last_name, authority, fk_workplace) VALUES (?, ?, ?, ?, ?)";

    private static final String STATEMENT_UPDATE = "UPDATE warehouse.employee SET time_card_number = ?, first_name = ?, last_name = ?, authority = ?, fk_workplace = ? WHERE warehouse.employee.time_card_number = ?";

    private static final String QUERY_GET_WORKPLACE_ID = "SELECT warehouse.warehouse.id_warehouse FROM warehouse.warehouse WHERE warehouse.warehouse.address = ? AND warehouse.warehouse.fk_location = ?";

    private static final String QUERY_GET_AUTHORITY_ID = "SELECT warehouse.authorities.id_authorities FROM warehouse.authorities WHERE warehouse.authorities.name = ?";

    private static final String STATEMENT_DELETE = "DELETE FROM warehouse.employee WHERE warehouse.employee.time_card_number = ?";
    // ***** END OF SQL STATEMENTS/QUERIES FOR EMPLOYEE TABLE ***** //

    /**
     * Default class constructor
     */
    public EmployeeDAO() {
        super();

        super.QUERY_GET_PAGE = this.QUERY_GET_PAGE;
        super.QUERY_COUNT = this.QUERY_COUNT;
    }

    /**
     * Converts a result set into a list
     *
     * @param result result set
     * @return list with records
     */
    @Override
    protected List<Employee> extractResultSet(ResultSet result) {
        List<Employee> employees = new ArrayList<>();

        try {
            while (result.next()) {
                employees.add(new Employee(
                        result.getLong("time_card_number"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("authority"),
                        result.getString("workplace") + ", " + result.getString("city")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return employees;
    }

    /**
     * Gets an employee by the time-card number
     * @param number
     * @return
     */
    public Employee getByNumber(Long number) {
        database.connect();
        database.prepareStatement(QUERY_GET_BY_NUMBER);

        try {
            PreparedStatement s = database.getStatement();

            s.setLong(1, number);

            ResultSet result = s.executeQuery();

            result.first();

            Employee employee = new Employee(
                    result.getLong("time_card_number"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("authority"),
                    result.getString("workplace") + ", " + result.getString("city")
            );

            database.disconnect();

            return employee;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all employee authorities
     * @return
     */
    public List<String> getAuthorities() {
        database.connect();
        database.prepareStatement(QUERY_GET_AUTHORITIES);

        ResultSet result = database.executeQuery(-1, new String[] {});

        List<String> authorities = new ArrayList<>();

        try {
            while (result.next()) {
                authorities.add(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();

        return authorities;
    }

    /**
     * Gets all available workplaces
     * @return
     */
    public List<String> getWorkplaces() {
        database.connect();
        database.prepareStatement(QUERY_GET_WORKPLACES);

        ResultSet result = database.executeQuery(-1, new String[] {});

        List<String> workplaces = new ArrayList<>();

        try {
            while (result.next()) {
                workplaces.add(result.getString("workplace") + ", " + result.getString("city"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();

        return workplaces;
    }

    /**
     * Saves a single object
     *
     * @param employee object to save
     * @return saved object
     */
    @Override
    public Employee save(Employee employee) {
        Long authority = getAuthorityId(employee.getAuthority());
        Long workplace = getWorkplaceId(employee.getWorkplace());

        database.connect();
        database.prepareStatement(STATEMENT_INSERT);

        try {
            PreparedStatement s = database.getStatement();

            setParameters(s, employee, authority, workplace);

            s.execute();

            database.disconnect();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return getByNumber(employee.getTimeCardNumber());
    }

    /**
     * Sets parameters for a prepared statement
     */
    private void setParameters(PreparedStatement s, Employee employee, Long authority, Long workplace) {
        try {
            s.setLong(1, employee.getTimeCardNumber());
            s.setString(2, employee.getFirstName());
            s.setString(3, employee.getLastName());
            s.setLong(4, authority);
            s.setLong(5, workplace);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets authority id
     * @return
     */
    private Long getAuthorityId(String authority) {
        database.connect();
        database.prepareStatement(QUERY_GET_AUTHORITY_ID);

        Long id = null;

        try {
            PreparedStatement s = database.getStatement();

            s.setString(1, authority);

            ResultSet r = s.executeQuery();

            r.first();

            id = r.getLong("id_authorities");

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Gets workplace id
     * @return
     */
    private Long getWorkplaceId(String workplace) {
        database.connect();
        database.prepareStatement(QUERY_GET_WORKPLACE_ID);

        Long id = null;

        try {
            PreparedStatement s = database.getStatement();

            String[] data = workplace.split(",");

            data[1] = data[1].trim();

            s.setString(1, data[0]);
            s.setString(2, data[1]);

            ResultSet r = s.executeQuery();

            r.first();

            id = r.getLong("id_warehouse");

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Updates an employee
     *
     * @param oldNumber
     * @param employee new information
     */
    public Employee update(Long oldNumber, Employee employee) {
        Long authority = getAuthorityId(employee.getAuthority());
        Long workplace = getWorkplaceId(employee.getWorkplace());

        database.connect();
        database.prepareStatement(STATEMENT_UPDATE);

        try {
            PreparedStatement s = database.getStatement();

            setParameters(s, employee, authority, workplace);
            s.setLong(6, oldNumber);

            s.execute();

            database.disconnect();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return getByNumber(employee.getTimeCardNumber());
    }

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    @Override
    public Employee delete(Long id) {
        Employee old = getByNumber(id);

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
}
