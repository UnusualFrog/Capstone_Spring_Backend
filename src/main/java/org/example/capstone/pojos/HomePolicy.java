package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an insurance policy for a {@link Home}.
 * <p>
 * Contains information on premium, coverage period, liability, customer, and policy status.
 */
@Entity
public class HomePolicy {

    /**
     * Unique identifier for the home policy.
     * */
    @Id
    @GeneratedValue
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    private double premium;
    private double basePremium;
    private int liabilityLimit;
    private double taxRate;
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
    private int custId;

    /**
     * Gets the policy ID.
     *
     * @return The unique policy ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the policy ID.
     *
     * @param id The ID to assign.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the effective (start) date of the policy.
     *
     * @return The start date.
     */
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the effective (start) date of the policy.
     *
     * @param effectiveDate The start date to assign.
     */
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the end date of the policy.
     *
     * @return The end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the policy.
     *
     * @param endDate The end date to assign.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the total premium (with tax).
     *
     * @return The premium amount.
     */
    public double getPremium() {
        return premium;
    }

    /**
     * Sets the total premium (with tax).
     *
     * @param premium The premium amount to assign.
     */
    public void setPremium(double premium) {
        this.premium = premium;
    }

    /**
     * Gets the liability limit of the policy.
     *
     * @return The liability amount.
     */
    public int getLiabilityLimit() {
        return liabilityLimit;
    }

    /**
     * Sets the liability limit of the policy.
     *
     * @param liabilityLimit The liability amount to assign.
     */
    public void setLiabilityLimit(int liabilityLimit) {
        this.liabilityLimit = liabilityLimit;
    }

    /**
     * Gets the tax rate applied to the policy.
     *
     * @return The tax rate.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate applied to the policy.
     *
     * @param taxRate The tax rate to assign.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Checks if the policy is currently active.
     *
     * @return True if active, false otherwise.
     */
    public boolean getActive() {
        return active; 
    }

    /**
     * Sets the active status of the policy.
     *
     * @param active True to activate; false to deactivate.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the associated home for this policy.
     *
     * @return The {@link Home} object.
     */
    public Home getHome() {
        return home;
    }

    /**
     * Sets the associated home for this policy.
     *
     * @param home The {@link Home} to associate.
     */
    public void setHome(Home home) {
        this.home = home;
    }

    /**
     * Gets the customer ID who owns this policy.
     *
     * @return The customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Sets the customer ID for this policy.
     *
     * @param custId The customer ID to assign.
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * Gets the base premium before tax or discounts.
     *
     * @return The base premium.
     */
    public double getBasePremium() {
        return basePremium;
    }

    /**
     * Sets the base premium amount.
     *
     * @param basePremium The base premium to assign.
     */
    public void setBasePremium(double basePremium) {
        this.basePremium = basePremium;
    }
}
