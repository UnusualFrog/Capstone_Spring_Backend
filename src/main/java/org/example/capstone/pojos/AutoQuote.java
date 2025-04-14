package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AutoQuote {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate generationDate;
    private boolean active = true;
    private double premium;
    private double taxRate;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public Auto getAuto() {
        return auto;
    }
}
