package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AutoPolicy {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate effectiveDate;
    private LocalDate endDate;
    private double premium;
    private double taxRate;
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPremium() {
        return premium;
    }

    public Auto getAuto() {
        return auto;
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

    public int getCustId() {
        return custId;
    }
}
