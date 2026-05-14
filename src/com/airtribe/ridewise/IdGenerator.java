package com.airtribe.ridewise;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private String prefix;
    private AtomicInteger counter;

    public IdGenerator(String prefix){
        this.prefix = prefix;
        this.counter = new AtomicInteger(1);
    }

    public String next(){
        return prefix + counter.getAndIncrement();
    }
}
