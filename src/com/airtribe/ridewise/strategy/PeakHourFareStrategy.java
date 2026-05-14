package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PeakHourFareStrategy extends DefaultFareStrategy{

    private static final LocalTime MORNING_PEAK_START = LocalTime.of(7,0);
    private static final LocalTime MORNING_PEAK_END = LocalTime.of(9,0);
    private static final LocalTime EVENING_PEAK_START = LocalTime.of(17,0);
    private static final LocalTime EVENING_PEAK_END = LocalTime.of(21,0);

    @Override
    public Double calculateFare(Ride ride){
        Double ratePerKm = super.calculateFare(ride);
        if(isPeakTime()){
            return ratePerKm * 1.5;
        }
        return ratePerKm;
    }

    private boolean isPeakTime(){
        LocalTime now = LocalTime.now();
        return isBetween(now, MORNING_PEAK_START,MORNING_PEAK_END) || isBetween(now, EVENING_PEAK_START, EVENING_PEAK_END);
    }

    private boolean isBetween(LocalTime now, LocalTime start, LocalTime end){
        return (now.isAfter(start) && now.isBefore(end));
    }
}
