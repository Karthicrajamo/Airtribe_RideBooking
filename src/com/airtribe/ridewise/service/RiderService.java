package com.airtribe.ridewise.service;

import com.airtribe.ridewise.IdGenerator;
import com.airtribe.ridewise.model.Rider;

import java.util.*;

public class RiderService {

    private final Map<String, Rider> riders = new LinkedHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator("R");

    public Rider registerRiders(String name, Double location) {
        String id = idGenerator.next();
        Rider rider = new Rider(id, name, location);
        riders.put(id,rider);
        System.out.println("Riders Registered : "+riders.keySet());
        return rider;
    }

    public Rider getRiderById(String id){
        return riders.get(id);
    }

    public List<Rider> getAllRiders(){
        return new ArrayList<>(riders.values());
    }

    public boolean isExist(String id){
        return riders.containsKey(id);
    }

}
