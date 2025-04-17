package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a quote for home insurance coverage.
 * Contains details such as premium amount, liability limit, generation date,
 * associated home, and the customer who received the quote.
 */
@Entity
public class HomeQuote {

    /**
     * Unique identifier for the home quote.
     * */
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

    /**
     * Gets the ID of the quote.
     * @return The quote ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the quote.
     * @param id The quote ID to assign.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the date this quote was generated.
     * @return The generation date.
     */
    public LocalDate getGenerationDate() {
        return generationDate;
    }

    /**
     * Sets the generation date of the quote.
     * @param generationDate The date to assign.
     */
    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    /**
     * Checks whether this quote is active.
     * @return True if active; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets the active status of the quote.
     * @param active True to mark as active; false otherwise.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the full premium cost (with tax).
     * @return The premium amount.
     */
    public double getPremium() {
        return premium;
    }

    /**
     * Sets the full premium cost.
     * @param premium The amount to set.
     */
    public void setPremium(double premium) {
        this.premium = premium;
    }

    /**
     * Gets the liability limit of the quote.
     * @return The liability coverage amount.
     */
    public int getLiabilityLimit() {
        return liabilityLimit;
    }

    /**
     * Sets the liability limit of the quote.
     * @param liabilityLimit The coverage amount to assign.
     */
    public void setLiabilityLimit(int liabilityLimit) {
        this.liabilityLimit = liabilityLimit;
    }

    /**
     * Gets the tax rate applied to this quote.
     * @return The tax rate percentage.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate applied to this quote.
     * @param taxRate The tax rate to assign.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Gets the associated {@link Home} entity.
     * @return The home for which the quote is generated.
     */
    public Home getHome() {
        return home;
    }

    /**
     * Sets the associated home for the quote.
     * @param home The {@link Home} to assign.
     */
    public void setHome(Home home) {
        this.home = home;
    }

    /**
     * Gets the base premium before tax or modifiers.
     * @return The base premium.
     */
    public double getBasePremium() {
        return basePremium;
    }

    /**
     * Sets the base premium before adjustments.
     * @param basePremium The base premium to assign.
     */
    public void setBasePremium(double basePremium) {
        this.basePremium = basePremium;
    }

    /**
     * Gets the customer ID associated with this quote.
     * @return The customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Sets the customer ID for this quote.
     * @param custId The customer ID to assign.
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }
}
