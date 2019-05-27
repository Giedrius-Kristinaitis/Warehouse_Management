package com.warehouse.dao;

import com.warehouse.Constants;
import com.warehouse.model.Client;
import com.warehouse.model.Contract;
import com.warehouse.model.ContractReport;
import com.warehouse.model.Employee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract data access object implementation
 */
@SuppressWarnings("Duplicates")
public class ContractDAO extends AbstractDAO<Contract> {

    // ********* SQL STATEMENTS/QUERIES FOR CONTRACT TABLE ******** //
    private static final String QUERY_GET_PAGE = "SELECT warehouse.contract.id, warehouse.contract.date, warehouse.contract.storing_from, warehouse.contract.storing_until, warehouse.contract.rented_area, warehouse.contract_statuses.name as status, warehouse.client.first_name as client_first, warehouse.client.last_name as client_last, warehouse.employee.first_name as employee_first, warehouse.employee.last_name as employee_last FROM warehouse.contract INNER JOIN warehouse.contract_statuses ON warehouse.contract.status = warehouse.contract_statuses.id_contract_statuses INNER JOIN warehouse.client ON warehouse.contract.fk_client = warehouse.client.personal_code INNER JOIN warehouse.employee ON warehouse.contract.fk_employee = warehouse.employee.time_card_number ORDER BY warehouse.contract.date LIMIT " + Constants.PAGE_SIZE + " OFFSET ?";

    private static final String QUERY_COUNT = "SELECT count(*) FROM warehouse.contract";

    private static final String QUERY_GET_CLIENTS = "SELECT warehouse.client.first_name, warehouse.client.last_name, warehouse.client.personal_code FROM warehouse.client";

    private static final String QUERY_GET_STATUSES = "SELECT warehouse.contract_statuses.name FROM warehouse.contract_statuses";

    private static final String QUERY_GET_EMPLOYEES = "SELECT warehouse.employee.time_card_number, warehouse.employee.first_name, warehouse.employee.last_name FROM warehouse.employee WHERE warehouse.employee.authority = 2";

    private static final String QUERY_GET_BY_ID = "SELECT warehouse.contract.id, warehouse.contract.date, warehouse.contract.storing_from, warehouse.contract.storing_until, warehouse.contract.rented_area, warehouse.contract.status FROM warehouse.contract WHERE warehouse.contract.id = ?";

    private static final String INSERT = "INSERT INTO warehouse.contract (id, date, storing_from, storing_until, rented_area, status, fk_client, fk_employee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE warehouse.contract SET date = ?, storing_from = ?, storing_until = ?, rented_area = ?, status = ?, fk_client = ?, fk_employee = ? WHERE warehouse.contract.id = ?";

    private static final String QUERY_GET_STATUS_ID = "SELECT warehouse.contract_statuses.id_contract_statuses FROM warehouse.contract_statuses WHERE warehouse.contract_statuses.name = ?";

    private static final String SAVE_BILL = "INSERT INTO warehouse.bill (id_bill, name, description, ammount, fk_contract) VALUES (?, ?, ?, ?, ?)";

    private static final String DELETE = "DELETE FROM warehouse.contract WHERE warehouse.contract.id = ?";

    private static final String DELETE_BILLS = "DELETE FROM warehouse.bill WHERE warehouse.bill.fk_contract = ?";

    private static final String QUERY_REPORT = "SELECT " +
                                                    "sub.status_name, " +
                                                    "sub.storing_from, " +
                                                    "sub.storing_until, " +
                                                    "sub.full_name, " +
                                                    "sub.bill, " +
                                                    "sub.excise, " +
                                                    "sub.group_bill, " +
                                                    "sub.group_excise, " +
                                                    "a.total_bill, " +
                                                    "b.total_excise " +
                                               "FROM (SELECT " +
                                                        "warehouse.contract_statuses.name as status_name," +
                                                        "ARRAY_AGG(warehouse.contract.storing_from) as storing_from, " +
                                                        "ARRAY_AGG(warehouse.contract.storing_until) as storing_until, " +
                                                        "ARRAY_AGG(CONCAT(warehouse.client.first_name, ' ', warehouse.client.last_name)) as full_name, " +
                                                        "ARRAY_AGG(warehouse.bill.ammount) as bill, " +
                                                        "ARRAY_AGG(warehouse.excise.amount) as excise, " +
                                                        "ROUND(CAST(SUM(warehouse.bill.ammount) as NUMERIC), 2) as group_bill, " +
                                                        "ROUND(CAST(SUM(warehouse.excise.amount) as NUMERIC), 2) as group_excise " +
                                                    "FROM warehouse.contract " +
                                                        "INNER JOIN warehouse.contract_statuses ON warehouse.contract.status = warehouse.contract_statuses.id_contract_statuses " +
                                                        "INNER JOIN warehouse.client ON warehouse.contract.fk_client = warehouse.client.personal_code " +
                                                        "INNER JOIN warehouse.employee ON warehouse.contract.fk_employee = warehouse.employee.time_card_number " +
                                                        "LEFT JOIN warehouse.bill ON warehouse.bill.fk_contract = warehouse.contract.id " +
                                                        "LEFT JOIN warehouse.excise ON warehouse.excise.fk_contract = warehouse.contract.id " +
                                                    "WHERE warehouse.contract.storing_from >= CAST(? as DATE) " +
                                                        "AND warehouse.contract.storing_until <= CAST(? as DATE) " +
                                                        "AND warehouse.contract.fk_employee = ? " +
                                                    "GROUP BY warehouse.contract_statuses.name " +
                                                    "ORDER BY full_name asc) as sub " +
                                                "CROSS JOIN (SELECT SUM(warehouse.bill.ammount) total_bill FROM warehouse.bill) as a " +
                                                "CROSS JOIN (SELECT SUM(warehouse.excise.amount) total_excise FROM warehouse.excise) as b";
    // ***** END OF SQL STATEMENTS/QUERIES FOR CONTRACT TABLE ***** //

    /**
     * Default class constructor
     */
    public ContractDAO() {
        super();

        super.QUERY_GET_PAGE = this.QUERY_GET_PAGE;
        super.QUERY_COUNT = this.QUERY_COUNT;
    }

    /**
     * Creates a contract report
     *
     * @param storingFrom the starting storing date
     * @param storingUntil the ending storing date
     * @param employee employee assigned to a contract
     * @return contract report
     */
    public List<ContractReport> getContractReport(String storingFrom, String storingUntil, String employee) {
        Long employeeId = Long.parseLong(employee.split(" ")[0]);

        List<ContractReport> reports = new ArrayList<>();

        database.connect();

        try {
            database.prepareStatement(QUERY_REPORT);

            PreparedStatement statement = database.getStatement();

            statement.setString(1, storingFrom);
            statement.setString(2, storingUntil);
            statement.setLong(3, employeeId);

            statement.execute();

            ResultSet result = statement.getResultSet();

            while (result.next()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Date[] storingDates = (Date[]) result.getArray("storing_from").getArray();
                String[] storingStringDates = new String[storingDates.length];

                for (int i = 0; i < storingDates.length; i++) {
                    storingStringDates[i] = format.format(storingDates[i]);
                }

                Date[] storingUntilDates = (Date[]) result.getArray("storing_until").getArray();
                String[] storingUntilStringDates = new String[storingUntilDates.length];

                for (int i = 0; i < storingUntilDates.length; i++) {
                    storingUntilStringDates[i] = format.format(storingUntilDates[i]);
                }

                ContractReport report = new ContractReport(
                        result.getString("status_name"),
                        storingStringDates,
                        storingUntilStringDates,
                        (String[]) result.getArray("full_name").getArray(),
                        (Double[]) result.getArray("bill").getArray(),
                        (Double[]) result.getArray("excise").getArray(),
                        result.getDouble("group_bill"),
                        result.getDouble("group_excise"),
                        result.getDouble("total_bill"),
                        result.getDouble("total_excise")
                );

                reports.add(report);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();

        return reports;
    }

    /**
     * Deletes bills
     * @param id contract id
     */
    private void deleteBills(Long id) {
        database.connect();

        try {
            database.prepareStatement(DELETE_BILLS);

            PreparedStatement s = database.getStatement();

            s.setLong(1, id);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            database.disconnect();
        }

        database.disconnect();
    }

    /**
     * Deletes an object
     *
     * @param id id of the object to delete
     * @return deleted object
     */
    @Override
    public Contract delete(Long id) {
        deleteBills(id);

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

        return new Contract();
    }

    /**
     * Saves a contract
     *
     * @param contract
     * @param client
     * @param employee
     * @return
     */
    public Contract save(Contract contract, String client, String employee) {
        Long statusID = getStatusId(contract.getStatus());

        database.connect();

        try {
            database.prepareStatement(INSERT);

            PreparedStatement s = database.getStatement();

            s.setLong(1, contract.getId());
            s.setDate(2, java.sql.Date.valueOf(contract.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDate(3, java.sql.Date.valueOf(contract.getStoringFrom().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDate(4, java.sql.Date.valueOf(contract.getStoringUntil().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDouble(5, contract.getRentedArea());
            s.setLong(6, statusID);

            // get client id
            String[] data = client.split(" ");
            Long clientId = Long.parseLong(data[data.length - 1]);

            // get employee id
            Long employeeId = Long.parseLong(employee.split(" ")[0]);

            s.setLong(7, clientId);
            s.setLong(8, employeeId);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            database.disconnect();
            return null;
        }

        database.disconnect();

        return new Contract();
    }

    /**
     * Saves a single bill
     *
     * @param contractId
     * @param name
     * @param description
     * @param amount
     */
    public void saveBill(Long contractId, String name, String description, double amount) {
        database.connect();

        try {
            database.prepareStatement(SAVE_BILL);

            PreparedStatement s = database.getStatement();

            s.setLong(1, System.currentTimeMillis());
            s.setString(2, name);
            s.setString(3, description);
            s.setDouble(4, amount);
            s.setLong(5, contractId);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();
    }

    /**
     * Updates a contract
     *
     * @param id
     * @param contract
     * @param client
     * @param employee
     * @return
     */
    public Contract update(Long id, Contract contract, String client, String employee) {
        Long statusID = getStatusId(contract.getStatus());

        database.connect();

        try {
            database.prepareStatement(UPDATE);

            PreparedStatement s = database.getStatement();

            s.setDate(1, java.sql.Date.valueOf(contract.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDate(2, java.sql.Date.valueOf(contract.getStoringFrom().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDate(3, java.sql.Date.valueOf(contract.getStoringUntil().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            s.setDouble(4, contract.getRentedArea());
            s.setLong(5, statusID);

            // get client id
            String[] data = client.split(" ");
            Long clientId = Long.parseLong(data[data.length - 1]);

            // get employee id
            Long employeeId = Long.parseLong(employee.split(" ")[0]);

            s.setLong(6, clientId);
            s.setLong(7, employeeId);
            s.setLong(8, id);

            s.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            database.disconnect();
            return null;
        }

        database.disconnect();

        return new Contract();
    }

    /**
     * Gets contract status' id
     * @param status
     * @return
     */
    private Long getStatusId(String status) {
        database.connect();
        database.prepareStatement(QUERY_GET_STATUS_ID);

        Long id = null;

        try {
            PreparedStatement s = database.getStatement();

            s.setString(1, status);

            ResultSet r = s.executeQuery();

            r.first();

            id = r.getLong("id_contract_statuses");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        database.disconnect();

        return id;
    }

    /**
     * Converts a result set into a list
     *
     * @param result result set
     * @return list with records
     */
    @Override
    protected List<Contract> extractResultSet(ResultSet result) {
        List<Contract> contracts = new ArrayList<>();

        try {
            while (result.next()) {
                contracts.add(new Contract(
                        result.getLong("id"),
                        result.getDate("date"),
                        result.getDate("storing_from"),
                        result.getDate("storing_until"),
                        result.getDouble("rented_area"),
                        result.getString("status"),
                        new Client(
                                0L,
                                result.getString("client_first"),
                                result.getString("client_last"),
                                null, null, null
                        ),
                        new Employee(
                                0L,
                                result.getString("employee_first"),
                                result.getString("employee_last"),
                                null, null
                        )
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return contracts;
    }

    /**
     * Gets a contract by it's id
     * @param id
     * @return
     */
    public Contract getById(Long id) {
        database.connect();
        database.prepareStatement(QUERY_GET_BY_ID);

        Contract contract = null;

        try {
            PreparedStatement s = database.getStatement();

            s.setLong(1, id);

            ResultSet r = s.executeQuery();

            r.first();

            contract = new Contract(
                    id,
                    r.getDate("date"),
                    r.getDate("storing_from"),
                    r.getDate("storing_until"),
                    r.getDouble("rented_area"),
                    r.getString("status"),
                    null,
                    null
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        database.disconnect();

        return contract;
    }

    /**
     * Gets all contract statuses
     * @return
     */
    public List<String> getStatuses() {
        List<String> statuses = new ArrayList<>();

        try {
            database.connect();
            database.prepareStatement(QUERY_GET_STATUSES);

            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                statuses.add(r.getString("name"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return statuses;
    }

    /**
     * Gets all clients
     * @return
     */
    public List<String> getClients() {
        database.connect();
        database.prepareStatement(QUERY_GET_CLIENTS);

        List<String> clients = new ArrayList<>();

        try {
            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                clients.add(r.getString("first_name") + " " + r.getString("last_name") + " " + r.getLong("personal_code"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return clients;
    }

    /**
     * Gets all employees
     * @return
     */
    public List<String> getEmployees() {
        List<String> employees = new ArrayList<>();

        try {
            database.connect();
            database.prepareStatement(QUERY_GET_EMPLOYEES);

            ResultSet r = database.executeQuery(-1, new String[] {});

            while (r.next()) {
                employees.add(r.getLong("time_card_number") + " " + r.getString("first_name") + " " + r.getString("last_name"));
            }

            database.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return employees;
    }
}
