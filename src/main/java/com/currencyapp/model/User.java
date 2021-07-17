package com.currencyapp.model;


import javax.persistence.*;
import java.util.Map;

@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Long pesel;
    private float amountPLN;
    private float amountUSD;


    public User() {
    }

    public User(Long id, String name, String surname, Long pesel, float amountPLN) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.amountPLN = amountPLN;
        this.amountUSD = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }


    public float getAmountPLN() {
        return amountPLN;
    }

    public void setAmountPLN(float amountPLN) {
        this.amountPLN = amountPLN;
    }

    public float getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(float amountUSD) {
        this.amountUSD = amountUSD;
    }
}
