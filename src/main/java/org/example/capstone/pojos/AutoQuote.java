package org.example.capstone.pojos;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an auto insurance quote generated for a specific {@link Auto}.
 * Includes premium information, generation date, active status, tax rate, and customer reference.
 */
@Entity
public class AutoQuote {

    /**
     * Unique identifier for the auto quote.
     * */
    @Id
    @GeneratedValue
    private int id;
    private LocalDate generationDate;
    private boolean active = true;
    private double premium;
    private double basePremium;
    private double taxRate;

    /**
     * The auto (vehicle) associated with this quote.
     * */
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
    private int custId;

    /**
     * Gets the ID of the quote.
     *
     * @return The quote ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the quote.
     *
     * @param id The quote ID to assign.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the date the quote was generated.
     *
     * @return The generation date.
     */
    public LocalDate getGenerationDate() {
        return generationDate;
    }

    /**
     * Sets the generation date of the quote.
     *
     * @param generationDate The {@link LocalDate} the quote was created.
     */
    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    /**
     * Checks if the quote is active.
     *
     * @return True if active; false otherwise.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets the active status of the quote.
     *
     * @param active True to mark as active; false to deactivate.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the final premium amount (after tax).
     *
     * @return The premium amount.
     */
    public double getPremium() {
        return premium;
    }

    /**
     * Sets the final premium amount (after tax).
     *
     * @param premium The premium to set.
     */
    public void setPremium(double premium) {
        this.premium = premium;
    }

    /**
     * Gets the tax rate applied to this quote.
     *
     * @return The tax rate.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the tax rate applied to this quote.
     *
     * @param taxRate The tax rate to set.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Gets the associated {@link Auto} for this quote.
     *
     * @return The vehicle being quoted.
     */
    public Auto getAuto() {
        return auto;
    }

    /**
     * Sets the associated {@link Auto} for this quote.
     *
     * @param auto The vehicle to associate.
     */
    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    /**
     * Gets the base premium before tax and adjustments.
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

    /**
     * Gets the customer ID who owns this quote.
     *
     * @return The customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * Sets the customer ID for this quote.
     *
     * @param custId The customer ID to assign.
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }
}
