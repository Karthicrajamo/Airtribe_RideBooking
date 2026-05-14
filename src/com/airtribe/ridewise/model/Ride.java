package com.airtribe.ridewise.model;

public class Ride {
    private Long id;
    private String rider;
    private String driver;
    private Double distance;
    private RideStatus rideStatus;

    public Ride(Long id, String rider, String driver, Double distance, RideStatus rideStatus) {
        this.id = id;
        this.rider = rider;
        this.driver = driver;
        this.distance = distance;
        this.rideStatus = rideStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
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
