package org.example.capstone.unused;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an Auto entity for the REST controller.
 * This entity demonstrates relationships in ORM and date fields.
 */
public class OLD_Auto {

    /**
     * Unique identifier for the Auto entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The date when the auto was built, formatted as YYYY-MM-DD.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBuilt;

    /**
     * The estimated value of the auto in integer format.
     */
    private int value;

    /**
     * The user associated with this auto. A user can own multiple autos.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private OLD_User user;

    /**
     * Gets the ID of the auto.
     * @return the auto ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the date the auto was built.
     * @return the date the auto was built.
     */
    public LocalDate getDateBuilt() {
        return dateBuilt;
    }

    /**
     * Sets the date the auto was built.
     * @param dateBuilt the build date to set.
     */
    public void setDateBuilt(LocalDate dateBuilt) {
        this.dateBuilt = dateBuilt;
    }

    /**
     * Gets the value of the auto.
     * @return the auto value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the auto.
     * @param value the value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the user associated with this auto.
     * @return the user who owns the auto.
     */
    public OLD_User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this auto.
     * @param user the user to set.
     */
    public void setUser(OLD_User user) {
        this.user = user;
    }
}
