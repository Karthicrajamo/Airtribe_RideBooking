package com.airtribe.ridewise.model;

public class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private Double distance;
    private RideStatus rideStatus;
    private FareReceipt fareReceipt;

    public Ride(String id, Rider rider, Driver driver, Double distance, RideStatus rideStatus) {
        this.id = id;
        this.rider = rider;
        this.driver = driver;
        this.distance = distance;
        this.rideStatus = rideStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public FareReceipt getFareReceipt() {
        return fareReceipt;
    }

    public void setFareReceipt(FareReceipt fareReceipt) {
        this.fareReceipt = fareReceipt;
    }

    @Override
    public String toString() {
        String driverInfo = (driver != null) ? driver.getName() : "Unassigned";
        String fareInfo = (fareReceipt != null)
                ? String.format("₹%.2f", fareReceipt.getAmount())
                : "N/A";
        return String.format("Ride[id=%s, rider=%s, driver=%s, distance=%.1fkm, status=%s, fare=%s]",
                id, rider.getName(), driverInfo, distance, rideStatus, fareInfo);

    }
}
