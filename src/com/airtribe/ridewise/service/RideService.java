package com.airtribe.ridewise.service;

import com.airtribe.ridewise.IdGenerator;
import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class RideService {

    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy rideMatchingStrategy;
    private final FareStrategy fareStrategy;

    private final IdGenerator idGenerator = new IdGenerator("RIDE");
    private final Map<String, Ride> rides = new LinkedHashMap<>();

    public RideService(RiderService riderService, DriverService driverService, RideMatchingStrategy rideMatchingStrategy, FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.rideMatchingStrategy = rideMatchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(String riderId, Double distance) throws NoDriverAvailableException {
        Rider rider = riderService.getRiderById(riderId);
        if(rider == null){
            throw new IllegalArgumentException("Rider not found");
        }

        if(driverService.getAvailableDrivers().isEmpty()){
            throw new NoDriverAvailableException("No drivers available at the moment");
        }

        Driver matchedDriver = rideMatchingStrategy.findDriver(rider, driverService.getAvailableDrivers());
        if(matchedDriver == null){
            throw new NoDriverAvailableException("No Suitable Ride found for this ride");
        }


    }
}
