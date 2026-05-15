package com.airtribe.ridewise.model;

public class Driver {
    private String id;
    private String name;
    private Double currentLocation;
    private boolean available;
    private VehicleType vehicleType;
    private int totalRidesCompleted;

    public Driver(String id, String name, Double currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;
        this.totalRidesCompleted = 0;
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getTotalRidesCompleted() {
        return totalRidesCompleted;
    }

    public void setTotalRidesCompleted(int totalRidesCompleted) {
        this.totalRidesCompleted = totalRidesCompleted;
    }

    public void incrementRidesCompleted(){
        this.totalRidesCompleted++;
    }

    @Override
    public String toString(){
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currentLocation=" + currentLocation +
                ", available=" + available +
                ", vehicleType=" + vehicleType +
                ", totalRidesCompleted=" + totalRidesCompleted +
                '}';
    }
}
