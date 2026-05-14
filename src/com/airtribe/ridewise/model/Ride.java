package com.airtribe.ridewise.model;

public class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private Double distance;
    private RideStatus rideStatus;

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
}
