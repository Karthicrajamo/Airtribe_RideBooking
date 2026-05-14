package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers){
        Driver nearestDriver = null;
        Double minDistance = Double.MAX_VALUE;

        for (Driver driver : availableDrivers){
            Double distance = Math.abs(driver.getCurrentLocation() - rider.getLocation());
            if(distance < minDistance){
                minDistance = distance;
                nearestDriver = driver;
            }
        }
        return nearestDriver;
    }
}
