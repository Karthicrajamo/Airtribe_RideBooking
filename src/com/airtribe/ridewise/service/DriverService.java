package com.airtribe.ridewise.service;

import com.airtribe.ridewise.IdGenerator;
import com.airtribe.ridewise.model.Driver;

import java.util.LinkedHashMap;
import java.util.Map;

public class DriverService {

    private final Map<String, Driver> drivers = new LinkedHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator("D");

    public Driver RegisterDriver(String name, Double currentLocation, boolean available){
        String id = idGenerator.next();
        Driver driver = new Driver(id, name,currentLocation, available);
    }
}
