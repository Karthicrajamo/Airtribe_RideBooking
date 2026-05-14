package com.airtribe.ridewise.model;

public class Driver {
    private String id;
    private String name;
    private Double currentLocation;
    private boolean available;
    private

    public Driver(String id, String name, Double currentLocation, boolean available) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.available = available;
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

    public Double getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Double currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
