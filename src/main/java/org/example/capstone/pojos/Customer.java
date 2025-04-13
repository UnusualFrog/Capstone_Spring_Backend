package org.example.capstone.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer extends User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int age;
    private String accidentCount;
    //private List insurableItems;
}
