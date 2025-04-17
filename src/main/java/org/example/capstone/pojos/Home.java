package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a home entity used for insurance quoting and policy generation.
 * <p>
 * Contains location, value, heating type, dwelling style, and relational references to the
 * {@link Customer} and {@link Address} entities.
 */
@Entity
public class Home {

    /**
     * Unique identifier for the home.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateBuilt;
    private double homeValue;
    @Enumerated(EnumType.ORDINAL)
    private HeatingType heatingType;
    private Location location;
    private DwellingType typeOfDwelling;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * The customer who owns this home. A customer may own multiple homes.
     * */
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;      //User can have many homes - this will maintain the relationship

    /**
     * Gets the home ID.
     * @return The unique home ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the home ID.
     * @return The unique home ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the construction date of the home.
     * @return The date built.
     */
    public LocalDate getDateBuilt() {
        return dateBuilt;
    }

    /**
     * Sets the construction date of the home.
     * @param yearBuilt The {@link LocalDate} the home was built.
     */
    public void setDateBuilt(LocalDate yearBuilt) {
        this.dateBuilt = yearBuilt;
    }

    /**
     * Gets the heating type used in the home.
     * @return The heating type.
     */
    public HeatingType getHeatingType() {
        return heatingType;
    }

    /**
     * Sets the heating type used in the home.
     * @param heatingType The heating type to set.
     */
    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }

    /**
     * Gets the location type (urban or rural).
     * @return The location type.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location type.
     * @param location The location enum to assign.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the customer who owns this home.
     * @return The associated {@link Customer}.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who owns this home.
     * @param customer The {@link Customer} to assign.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the declared value of the home.
     * @return The home value.
     */
    public double getHomeValue() {
        return homeValue;
    }

    /**
     * Sets the declared value of the home.
     * @param homeValue The monetary value.
     */
    public void setHomeValue(double homeValue) {
        this.homeValue = homeValue;
    }

    /**
     * Gets the address associated with the home.
     * @return The {@link Address}.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address for the home.
     * @param address The {@link Address} to assign.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the dwelling type of the home (e.g., standalone, bungalow).
     * @return The dwelling type.
     */
    public DwellingType getTypeOfDwelling() {
        return typeOfDwelling;
    }

    /**
     * Sets the type of dwelling for the home.
     * @param typeOfDwelling The dwelling type enum.
     */
    public void setTypeOfDwelling(DwellingType typeOfDwelling) {
        this.typeOfDwelling = typeOfDwelling;
    }

    /**
     * Heating type enum
     * {@link #OIL_HEATING}
     * {@link #WOOD_HEATING}
     * {@link #OTHER_HEATING}
     */
    public enum HeatingType {
        /**
         * Oil Heating
         */
        OIL_HEATING,
        /**
         * Wood Heating
         */
        WOOD_HEATING,
        /**
         * Other Heating
         */
        ELECTRIC_HEATING,
        /**
         * Other Heating
         */
        GAS_HEATING,
        /**
         * Other Heating
         */
        OTHER_HEATING
    }

    /**
     * Location
     * {@link #URBAN}
     * {@link #RURAL}
     */
    public enum Location {
        /**
         * Urban Location
         */
        URBAN,
        /**
         * Rural Location
         */
        RURAL
    }

    /**
     * Dwelling Type
     * {@link #STANDALONE}
     * {@link #BUNGALOW}
     * {@link #ATTACHED}
     * {@link #SEMI_ATTACHED}
     * {@link #OTHER_DWELLING}
     */
    public enum DwellingType {
        /**
         * Standalone Dwelling
         */
        STANDALONE,
        /**
         * Bungalow Dwelling
         */
        BUNGALOW,
        /**
         * Attached Dwelling
         */
        ATTACHED,
        /**
         * Semi-attached Dwelling
         */
        SEMI_ATTACHED,
        /**
         * Other Dwelling types
         */
        OTHER_DWELLING
    }

}
