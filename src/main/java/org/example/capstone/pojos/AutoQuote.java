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
    private double basePremium;
    private double taxRate;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
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
