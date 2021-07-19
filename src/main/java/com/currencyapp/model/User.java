package com.currencyapp.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String pesel;
    private BigDecimal amountPLN;
    private BigDecimal amountUSD;

    public User() {
    }

    public User(Long id, String name, String surname, String pesel, BigDecimal amountPLN, BigDecimal amountUSD) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.amountPLN = amountPLN;
        this.amountUSD = amountUSD;
    }

    public User(String name, String surname, String pesel, BigDecimal amountPLN, BigDecimal amountUSD) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.amountPLN = amountPLN;
        this.amountUSD = amountUSD;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public BigDecimal getAmountPLN() {
        return amountPLN;
    }

    public void setAmountPLN(BigDecimal amountPLN) {
        this.amountPLN = amountPLN;
    }

    public BigDecimal getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(BigDecimal amountUSD) {
        this.amountUSD = amountUSD;
    }
}
