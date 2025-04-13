package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a Home entity for the REST controller.
 * This entity demonstrates relationships in ORM, enum fields, and date fields.
 */
@Entity
public class OLD_Home {

    /**
     * Unique identifier for the Home entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The date when the home was built, formatted as YYYY-MM-DD.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBuilt;

    /**
     * The estimated value of the home in integer format.
     */
    private int value;

    /**
     * The type of heating system used in the home.
     */
    @Enumerated(EnumType.ORDINAL)
    private HeatingType heatingType;

    /**
     * The location type of the home (Urban or Rural).
     */
    private Location location;

    /**
     * The user associated with this home. A user can own multiple homes.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private OLD_User user;

    /**
     * Gets the ID of the home.
     * @return the home ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the date the home was built.
     * @return the date the home was built.
     */
    public LocalDate getDateBuilt() {
        return dateBuilt;
    }

    /**
     * Sets the date the home was built.
     * @param dateBuilt the build date to set.
     */
    public void setDateBuilt(LocalDate dateBuilt) {
        this.dateBuilt = dateBuilt;
    }

    /**
     * Gets the value of the home.
     * @return the home's value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the home.
     * @param value the value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the heating type of the home.
     * @return the heating type.
     */
    public HeatingType getHeatingType() {
        return heatingType;
    }

    /**
     * Sets the heating type of the home.
     * @param heatingType the heating type to set.
     */
    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }

    /**
     * Gets the location of the home.
     * @return the location type (Urban or Rural).
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the home.
     * @param location the location type to set.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the user associated with this home.
     * @return the user who owns the home.
     */
    public OLD_User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this home.
     * @param user the user to set.
     */
    public void setUser(OLD_User user) {
        this.user = user;
    }

    /**
     * Enum representing different types of heating systems in a home.
     */
    public enum HeatingType {
        /**
         * Oil-based heating system.
         */
        OIL_HEATING,

        /**
         * Wood-based heating system.
         */
        WOOD_HEATING,

        /**
         * Other types of heating systems.
         */
        OTHER_HEATING
    }

    /**
     * Enum representing different types of home locations.
     */
    public enum Location {
        /**
         * Urban location type.
         */
        URBAN,

        /**
         * Rural location type.
         */
        RURAL
    }
}