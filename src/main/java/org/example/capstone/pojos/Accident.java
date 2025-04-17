package org.example.capstone.pojos;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a record of an accident associated with a {@link Customer}.
 * <p>
 * Used in insurance quote and policy calculations.
 */
@Entity
public class Accident {

    /**
     * The unique identifier for the accident record.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * The date on which the accident occurred.
     */
    private LocalDate date;

    /**
     * The customer associated with the accident.
     * This is a many-to-one relationship; a customer may have multiple accidents.
     */
    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    /**
     * Gets the accident ID.
     *
     * @return The ID of the accident.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the accident ID.
     *
     * @param id The new ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the date of the accident.
     *
     * @return The {@link LocalDate} the accident occurred.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the accident.
     *
     * @param date The {@link LocalDate} the accident occurred.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the customer associated with this accident.
     *
     * @return The {@link Customer} involved.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this accident.
     *
     * @param customer The {@link Customer} to associate.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
