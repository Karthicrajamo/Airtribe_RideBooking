package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.VehicleType;

public class DefaultFareStrategy implements FareStrategy{
    @Override
    public Double calculateFare(Ride ride){
        Double ratePerKm = getRatePerKm(ride);
        return ratePerKm * ride.getDistance();

    }

    private Double getRatePerKm(Ride ride){
        VehicleType vehicleType = ride.getDriver().getVehicleType();
        return switch(vehicleType) {
            case BIKE -> 10.0;
            case CAR -> 20.0;
            case AUTO -> 30.0;
        };
    }
}
