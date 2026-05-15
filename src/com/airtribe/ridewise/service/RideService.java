package com.airtribe.ridewise.service;

import com.airtribe.ridewise.IdGenerator;
import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.Collection;
import java.util.Collections;
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
        String id = idGenerator.next();
        Ride ride = new Ride(id, rider, matchedDriver,distance, RideStatus.ASSIGNED);

        driverService.setAvailablity(matchedDriver.getId(),false);

        rides.put(id, ride);

        System.out.println("Ride assigned: "+ride.getId() +" -> Driver: "+matchedDriver.getName());
        return ride;
    }

    public FareReceipt completeRide(String rideId){
        Ride ride = rides.get(rideId);
        if(ride == null){
            throw new IllegalArgumentException("Ride not found");
        }

        if(ride.getRideStatus() != RideStatus.ASSIGNED){
            throw new IllegalArgumentException("Only ASSIGNED status can be Completed. Current status: "+ride.getRideStatus());
        }
        Double amount = fareStrategy.calculateFare(ride);

        FareReceipt fareReceipt = new FareReceipt(rideId, amount);
        ride.setFareReceipt(fareReceipt);
        ride.setRideStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();
        driverService.setAvailablity(driver.getId(),true);
        driver.incrementRidesCompleted();

        System.out.println("Ride completed: "+ride.getId() +" -> Fare: "+amount);
        return fareReceipt;
    }

    public void cancelRide(String rideId){
        Ride ride = rides.get(rideId);
        if(ride == null) throw new IllegalArgumentException("Ride not found");

        if(ride.getRideStatus() == RideStatus.COMPLETED){
            throw new IllegalArgumentException("Completed Ride cannot be cancel");
        }

        if(ride.getRideStatus() == RideStatus.ASSIGNED && ride.getDriver() != null){
            driverService.setAvailablity(ride.getDriver().getId(),true);
        }
        ride.setRideStatus(RideStatus.CANCELLED);

        System.out.println("Ride cancelled: "+ride.getId());
    }

    public Collection<Ride> getAllRides(){
        return Collections.unmodifiableCollection(rides.values());
    }

    public Ride getRideById(String id){
        return rides.get(id);
    }

    public RideService withMatchingStrategy(RideMatchingStrategy newStrategy){
        return new RideService(riderService, driverService, newStrategy, fareStrategy);
    }

    public RideService withFareStrategy(FareStrategy newFareStrategy){
        return new RideService(riderService, driverService, rideMatchingStrategy, newFareStrategy);
    }

}
