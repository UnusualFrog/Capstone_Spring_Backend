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
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

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

    public Home getHome() {
        return home;
    }
}
