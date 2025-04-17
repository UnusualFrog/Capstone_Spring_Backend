package org.example.capstone.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a physical address used for customers, homes, or policies.
 * Contains unit number, street, city, province, and postal code information.
 */
@Entity
public class Address {

    /**
     * Unique identifier for the address.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * Unit or apartment number of the address.
     */
    private Integer unit;

    /**
     * Street name of the address.
     */
    private String street;

    /**
     * City of the address.
     */
    private String city;

    /**
     * Province or state of the address.
     */
    private String province;

    /**
     * Postal or ZIP code of the address.
     */
    private String postalCode;

    /**
     * Gets the ID of the address.
     * @return The address ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the address.
     * @param id The address ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a formatted address string.
     * @return The address as a readable string.
     */
    public String toString() {
        return unit + " " + street + "\n" + city + ", " + province + " " + postalCode;
    }

    /**
     * Gets the unit number.
     * @return The unit or apartment number.
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * Sets the unit number.
     * @param unit The unit or apartment number.
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * Gets the street name.
     * @return The street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name.
     * @param street The street to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the city name.
     * @return The city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city name.
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the province name.
     * @return The province or state.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province name.
     * @param province The province or state to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Gets the postal code.
     * @return The postal or ZIP code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code.
     * @param zip The postal or ZIP code to set.
     */
    public void setPostalCode(String zip) {
        this.postalCode = zip;
    }
}
