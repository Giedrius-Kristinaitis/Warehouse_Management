package com.warehouse.model;

import java.util.Date;

/**
 * Contract model
 */
public class Contract {

    private Long id;
    private Date date;
    private Date storingFrom;
    private Date storingUntil;
    private double rentedArea;
    private String status;
    private Client client;
    private Employee employee;

    /**
     * Default class constructor
     */
    public Contract() {}

    /**
     * Constructor with arguments
     *
     * @param id
     * @param date
     * @param storingFrom
     * @param storingUntil
     * @param rentedArea
     * @param status
     * @param client
     * @param employee
     */
    public Contract(Long id, Date date, Date storingFrom, Date storingUntil, double rentedArea, String status, Client client, Employee employee) {
        this.id = id;
        this.date = date;
        this.storingFrom = storingFrom;
        this.storingUntil = storingUntil;
        this.rentedArea = rentedArea;
        this.status = status;
        this.client = client;
        this.employee = employee;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Date getStoringFrom() {
        return storingFrom;
    }

    public Date getStoringUntil() {
        return storingUntil;
    }

    public double getRentedArea() {
        return rentedArea;
    }

    public String getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Employee getEmployee() {
        return employee;
    }

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStoringFrom(Date storingFrom) {
        this.storingFrom = storingFrom;
    }

    public void setStoringUntil(Date storingUntil) {
        this.storingUntil = storingUntil;
    }

    public void setRentedArea(double rentedArea) {
        this.rentedArea = rentedArea;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
