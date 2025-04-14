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
    private Integer accidentCount;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    // @JsonIgnore //hides from json / removable if necessary
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getAccidentCount() {
        return accidentCount;
    }

    public void setAccidentCount(Integer accidentCount) {
        this.accidentCount = accidentCount;
    }

    /**
     * Get the name
     * @return name
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
