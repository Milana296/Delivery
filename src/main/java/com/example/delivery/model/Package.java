
package com.example.delivery.model;

public class Package {
    private final int id;
    private final String description;
    private final Customer customer;

    public Package(int id, String description, Customer customer) {
        this.id = id;
        this.description = description;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Customer getCustomer() {
        return customer;
    }
}
