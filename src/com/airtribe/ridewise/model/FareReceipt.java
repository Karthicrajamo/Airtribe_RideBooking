package com.airtribe.ridewise.model;

import java.time.LocalDateTime;

public class FareReceipt {
    private Long rideId;
    private Double amount;
    private LocalDateTime generatedAt;

    public FareReceipt(Long rideId, Double amount, LocalDateTime generatedAt) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = generatedAt;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
