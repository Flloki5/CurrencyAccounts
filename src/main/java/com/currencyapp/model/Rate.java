package com.currencyapp.model;

import java.math.BigDecimal;

public class Rate {

    public BigDecimal mid;

    public Rate(BigDecimal mid) {
        this.mid = mid;
    }

    public Rate() {
    }

    public BigDecimal getMid() {
        return mid;
    }

    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }
}
