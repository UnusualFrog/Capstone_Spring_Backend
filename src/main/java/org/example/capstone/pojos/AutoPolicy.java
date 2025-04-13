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
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

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

    public Auto getAuto() {
        return auto;
    }
}
