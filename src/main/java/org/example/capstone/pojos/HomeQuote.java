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

    public int getLiabilityLimit() {
        return liabilityLimit;
    }

    public double getPremium() {
        return premium;
    }

    public Home getHome() {
        return home;
    }
}
