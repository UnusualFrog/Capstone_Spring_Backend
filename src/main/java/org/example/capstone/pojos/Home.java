package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Home object for REST Assignment - meant to be used for capstone.Capstone project (change as needed).
 * This object will demonstrate a relationship in the ORM and enum and date fields
 */
@Entity
public class Home {

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

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;      //User can have many homes - this will maintain the relationship

    //TODO Document Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateBuilt() {
        return dateBuilt;
    }

    public void setDateBuilt(LocalDate yearBuilt) {
        this.dateBuilt = yearBuilt;
    }

    public HeatingType getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getHomeValue() {
        return homeValue;
    }

    public void setHomeValue(double homeValue) {
        this.homeValue = homeValue;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DwellingType getTypeOfDwelling() {
        return typeOfDwelling;
    }

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
