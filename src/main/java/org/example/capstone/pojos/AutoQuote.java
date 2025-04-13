package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AutoQuote {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate generationDate;
    private boolean active;
    private double premium;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

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

    public Auto getAuto() {
        return auto;
    }
}
