package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HomePolicy {

    @Id
    @GeneratedValue
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    private double premium;
    private int liabilityLimit;
    private double taxRate;
    private boolean active = true;
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

    public boolean getActive() {
        return active; 
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }

    public Home getHome() {
        return home;
    }

    public int getCustId() {
        return custId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public void setLiabilityLimit(int liabilityLimit) {
        this.liabilityLimit = liabilityLimit;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
}
