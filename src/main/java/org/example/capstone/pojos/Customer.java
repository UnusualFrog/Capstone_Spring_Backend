package org.example.capstone.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    @JsonIgnore //hides from json / removable if necessary
    private String password;

    /**
     * Represents a customer in the insurance system.
     * <p>
     * Contains basic personal and contact details including address and authentication credentials.
     */
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * Gets the customer ID.
     *
     * @return The unique customer ID.
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the customer's birthday.
     *
     * @return The {@link LocalDate} of birth.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the customer's birthday.
     *
     * @param birthday The date of birth to set.
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the full name of the customer.
     *
     * @return A string combining first and last name.
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * Gets the first name.
     *
     * @return First name string.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return Last name string.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address.
     *
     * @return Email string.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email The email to assign.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username used for login.
     *
     * @return The username string.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username used for login.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the encrypted password.
     *
     * @return Password string (encrypted).
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password.
     *
     * @param password The encrypted password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the customer's address.
     *
     * @return The associated {@link Address} entity.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     *
     * @param address The {@link Address} to assign.
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
