package com.warehouse.model;

import java.util.Date;

/**
 * Client model
 */
public class Client {

    private Long personalCode;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date birthDate;

    /**
     * Default no-args constructor
     */
    public Client() {}

    /**
     * Constructor with arguments
     *
     * @param personalCode
     * @param firstName
     * @param lastName
     * @param phone
     * @param email
     * @param birthDate
     */
    public Client(Long personalCode, String firstName, String lastName, String phone, String email, Date birthDate) {
        this.personalCode = personalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
    }

    // GETTERS
    public Long getPersonalCode() { return personalCode; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Date getBirthDate() { return birthDate; }

    // SETTERS
    public void setPersonalCode(Long personalCode) { this.personalCode = personalCode; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
}
