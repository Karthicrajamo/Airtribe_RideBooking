package com.airtribe.ridewise.model;

import com.airtribe.ridewise.IdGenerator;

public class Rider {
    private String id;
    private String name;
    private Double location;

    public Rider(String id, String name, Double location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLocation() {
        return location;
    }

    public void setLocation(Double location) {
        this.location = location;
    }
}
