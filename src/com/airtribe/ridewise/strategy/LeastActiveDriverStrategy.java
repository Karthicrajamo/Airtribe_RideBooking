package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers){
        Driver leastActiveDriver = null;
        int minRider = Integer.MAX_VALUE;

        for( Driver driver : availableDrivers){
            if(driver.getTotalRidesCompleted() < minRider){
                minRider = driver.getTotalRidesCompleted();
                leastActiveDriver = driver;
            }
        }
        return leastActiveDriver;
    }
}
