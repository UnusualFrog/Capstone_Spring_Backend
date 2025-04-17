package org.example.capstone.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an employee in the system.
 * <p>
 * Contains user credentials and a flag to identify administrator privileges.
 */
@Entity
public class Employee {

    /**
     * Unique identifier for the employee.
     * */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    // @JsonIgnore //hides from json / removable if necessary
    private String password;

    /**
     * Boolean flag to indicate if the employee has admin privileges.
     * */
    private boolean admin = false;

    /**
     * Gets the employee ID.
     *
     * @return The unique employee ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the employee ID.
     *
     * @param id The ID to assign.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the first name of the employee.
     *
     * @return First name string.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the employee.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the employee.
     *
     * @return Last name string.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the employee.
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
     * Sets the login username.
     *
     * @param username The username to assign.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the employee's encrypted password.
     *
     * @return The password string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the employee's encrypted password.
     *
     * @param password The encrypted password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the employee has admin privileges.
     *
     * @return True if admin, false otherwise.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets the admin privilege flag.
     *
     * @param admin True to grant admin access, false to revoke.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
