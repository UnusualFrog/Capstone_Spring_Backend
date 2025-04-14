package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HomePolicy {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate effectiveDate;
    private LocalDate endDate;
    private double premium;
    private int liabilityLimit;
    private double taxRate;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
    private int custId;

    public int getId() {
        return id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPremium() {
        return premium;
    }

    public int getLiabilityLimit() {
        return liabilityLimit;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public Home getHome() {
        return home;
    }

    public int getCustId() {
        return custId;
    }
}
