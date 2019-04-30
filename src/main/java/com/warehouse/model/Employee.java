package com.warehouse.model;

/**
 * Employee model
 */
public class Employee {

    private Long timeCardNumber;
    private String firstName;
    private String lastName;
    private String authority;
    private String workplace;

    /**
     * Default class constructor
     */
    public Employee() { }

    /**
     * Constructor with arguments
     *
     * @param timeCardNumber
     * @param firstName
     * @param lastName
     * @param authority
     * @param workplace
     */
    public Employee(Long timeCardNumber, String firstName, String lastName, String authority, String workplace) {
        this.timeCardNumber = timeCardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authority = authority;
        this.workplace = workplace;
    }

    // GETTERS
    public Long getTimeCardNumber() { return timeCardNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAuthority() { return authority; }
    public String getWorkplace() { return workplace; }

    // SETTERS
    public void setTimeCardNumber(Long timeCardNumber) { this.timeCardNumber = timeCardNumber; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAuthority(String authority) { this.authority = authority; }
    public void setWorkplace(String workplace) { this.workplace = workplace; }
}
