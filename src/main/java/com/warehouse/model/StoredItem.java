package com.warehouse.model;

/**
 * Stored item model
 */
public class StoredItem {

    private Long id;
    private String name;
    private String description;
    private String dimensions;
    private double weight;
    private String category;
    private int count;
    private Client owner;
    private String storageAddress;

    private double width;
    private double length;
    private double height;

    /**
     * Default class constructor
     */
    public StoredItem() {}

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param dimensions
     * @param width
     * @param length
     * @param height
     * @param weight
     * @param category
     * @param count
     * @param owner
     * @param storageAddress
     */
    public StoredItem(Long id, String name, String description, String dimensions, double width, double length, double height, double weight, String category, int count, Client owner, String storageAddress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.category = category;
        this.count = count;
        this.owner = owner;
        this.storageAddress = storageAddress;
    }

    // GETTERS
    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public int getCount() {
        return count;
    }

    public Client getOwner() {
        return owner;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public String getCategory() {
        return category;
    }

    // SETTERS
    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }
}
