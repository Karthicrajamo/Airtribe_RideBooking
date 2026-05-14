package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

public interface FareStrategy {
    Double calculateFare(Ride ride);
}
