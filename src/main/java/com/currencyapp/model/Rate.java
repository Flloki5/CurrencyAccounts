package com.currencyapp.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;

public class Rate {

    @JsonSetter("bid")
    public BigDecimal sellRate;

    @JsonSetter("ask")
    public BigDecimal buyRate;


    public Rate(BigDecimal sellRate, BigDecimal buyRate) {
        this.sellRate = sellRate;
        this.buyRate = buyRate;
    }

    public Rate() {
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }
}
