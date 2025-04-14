package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HomeQuote {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate generationDate;
    private boolean active;
    private double premium;
    private int liabilityLimit;
    private double taxRate;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    public int getId() {
        return id;
    }

    public LocalDate getGenerationDate() {
        return generationDate;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public void setHome(Home home) {
        this.home = home;
    }
}
