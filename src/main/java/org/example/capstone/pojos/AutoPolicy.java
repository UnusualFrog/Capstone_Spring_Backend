package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an auto insurance policy associated with a specific {@link Auto}.
 * <p>
 * Contains policy details such as premium, effective and end dates, tax rate, and customer linkage.
 */
@Entity
public class AutoPolicy {

    /**
     * Unique identifier for the auto policy.
     * */
    @Id
    @GeneratedValue
    private int id;
    private LocalDate effectiveDate;
    private LocalDate endDate;
    private double premium;
    private double basePremium;
    private double taxRate;
    private boolean active = true;

    /**
     * Associated vehicle for this policy.
     * */
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
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
     * Gets the policy's effective (start) date.
     *
     * @return The effective date.
     */
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the policy's effective (start) date.
     *
     * @param effectiveDate The effective date to set.
     */
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets the policy's end date.
     *
     * @return The end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the policy's end date.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the full premium (with tax).
     *
     * @return The premium amount.
     */
    public double getPremium() {
        return premium;
    }

    /**
     * Sets the full premium (with tax).
     *
     * @param premium The premium amount to set.
     */
    public void setPremium(double premium) {
        this.premium = premium;
    }

    /**
     * Gets the auto (vehicle) associated with this policy.
     *
     * @return The {@link Auto} object.
     */
    public Auto getAuto() {
        return auto;
    }

    /**
     * Sets the auto (vehicle) associated with this policy.
     *
     * @param auto The {@link Auto} object to set.
     */
    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    /**
     * Gets the tax rate applied to this policy.
     *
     * @return The tax rate.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate applied to this policy.
     *
     * @param taxRate The tax rate to set.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Checks whether this policy is active.
     *
     * @return True if active; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets whether this policy is active.
     *
     * @param active True to activate; false to deactivate.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the customer ID associated with this policy.
     *
     * @return The customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Sets the customer ID associated with this policy.
     *
     * @param custId The customer ID to set.
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * Gets the base premium before tax and risk adjustments.
     *
     * @return The base premium.
     */
    public double getBasePremium() {
        return basePremium;
    }

    /**
     * Sets the base premium before adjustments.
     *
     * @param basePremium The base premium amount.
     */
    public void setBasePremium(double basePremium) {
        this.basePremium = basePremium;
    }
}
