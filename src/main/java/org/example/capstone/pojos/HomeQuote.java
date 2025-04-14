package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HomeQuote {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate generationDate;
    private boolean active = true;
    private double premium;
    private double basePremium;
    private int liabilityLimit;
    private double taxRate;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
    private int custId;

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

    public void setLiabilityLimit(int liabilityLimit) {
        this.liabilityLimit = liabilityLimit;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public double getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(double basePremium) {
        this.basePremium = basePremium;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
}
