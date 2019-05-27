package com.warehouse.model;

/**
 * Represents a report on contracts
 */
public class ContractReport {

    private String status;
    private String[] storingFrom;
    private String[] storingUntil;
    private String[] fullName;
    private Double[] bills;
    private Double[] excises;
    private Double groupBill;
    private Double groupExcise;
    private static Double totalBill;
    private static Double totalExcise;

    public ContractReport() {}

    public ContractReport(String status, String[] storingFrom, String[] storingUntil, String[] fullName, Double[] bills, Double[] excises, Double groupBill, Double groupExcise, Double totalBill, Double totalExcise) {
        this.status = status;
        this.storingFrom = storingFrom;
        this.storingUntil = storingUntil;
        this.fullName = fullName;
        this.bills = bills;
        this.excises = excises;
        this.groupBill = groupBill;
        this.groupExcise = groupExcise;
        this.totalBill = totalBill;
        this.totalExcise = totalExcise;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getStoringFrom() {
        return storingFrom;
    }

    public void setStoringFrom(String[] storingFrom) {
        this.storingFrom = storingFrom;
    }

    public String[] getStoringUntil() {
        return storingUntil;
    }

    public void setStoringUntil(String[] storingUntil) {
        this.storingUntil = storingUntil;
    }

    public String[] getFullName() {
        return fullName;
    }

    public void setFullName(String[] fullName) {
        this.fullName = fullName;
    }

    public Double[] getBills() {
        return bills;
    }

    public void setBills(Double[] bills) {
        this.bills = bills;
    }

    public Double[] getExcises() {
        return excises;
    }

    public void setExcises(Double[] excises) {
        this.excises = excises;
    }

    public Double getGroupBill() {
        return groupBill;
    }

    public void setGroupBill(Double groupBill) {
        this.groupBill = groupBill;
    }

    public Double getGroupExcise() {
        return groupExcise;
    }

    public void setGroupExcise(Double groupExcise) {
        this.groupExcise = groupExcise;
    }

    public static Double getTotalBill() {
        return totalBill;
    }

    public static void setTotalBill(Double totalBill) {
        ContractReport.totalBill = totalBill;
    }

    public static Double getTotalExcise() {
        return totalExcise;
    }

    public static void setTotalExcise(Double totalExcise) {
        ContractReport.totalExcise = totalExcise;
    }
}
