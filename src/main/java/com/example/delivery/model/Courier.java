
package com.example.delivery.model;

public class Courier {
    private final int id;
    private final String name;

    public Courier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
