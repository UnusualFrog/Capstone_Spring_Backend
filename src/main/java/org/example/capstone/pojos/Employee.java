package org.example.capstone.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee extends User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
}
