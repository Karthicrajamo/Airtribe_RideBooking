package com.airtribe.ridewise.service;

import com.airtribe.ridewise.IdGenerator;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.VehicleType;

import java.util.*;

public class DriverService extends Exception {

    private final Map<String, Driver> drivers = new LinkedHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator("D");

    public Driver RegisterDriver(String name, Double currentLocation, VehicleType vehicleType) {
        String id = idGenerator.next();
        Driver driver = new Driver(id, name,currentLocation, vehicleType);
        drivers.put(id,driver);
        System.out.println("Driver registered: " + driver.getName() + " with ID: " + driver.getId());
        return driver;
    }

    public Driver getDriverById(String id){
        return drivers.get(id);
    }

    public Collection<Driver> getAllDrivers() {
        return Collections.unmodifiableCollection(drivers.values());
    }

    public List<Driver> getAvailableDrivers(){
        List<Driver> availableDrivers = new ArrayList<>();

        for(Driver driver : drivers.values()){
            if(driver.isAvailable())
                availableDrivers.add(driver);
        }
        return availableDrivers;
    }

    public Driver setAvailablity(String id, boolean available){
        if(!exist(id)) {
            throw new RuntimeException("Driver with ID " + id + " does not exist.");
        }

        Driver driver = drivers.get(id);
        driver.setAvailable(available);
        return driver;
    }

    public boolean exist(String id){
        return drivers.containsKey(id);
    }
}
