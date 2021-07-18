package com.currencyapp.controller;

import com.currencyapp.client.NbpClient;
import com.currencyapp.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;

@RestController
@RequestMapping(value = "/api/v1/currency")
public class CurrencyController {

    private final NbpClient nbpClient;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public CurrencyController(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    @GetMapping(value = "/{code}")
    @ResponseBody
    public Currency getCurrencyDataByCode(@PathVariable String code){
        Currency nbpCurrency = null;
        try{
            nbpCurrency = nbpClient.getNbpCurrency(code);
        } catch (Exception e){
            log.error("API NBP error", e);
        }
        return nbpCurrency;
    }

    @GetMapping(value = "/exchange/{from}/{to}/{value}")
    @ResponseBody
    public BigDecimal calculateExchange(@PathVariable BigDecimal value,
                                   @PathVariable String from,
                                   @PathVariable String to){
        if(to.equalsIgnoreCase("PLN")){
            Currency targetCurrency = getCurrencyDataByCode(from);
            return value.multiply(targetCurrency.getRates().get(0).getSellRate());
        }else{
            Currency targetCurrency = getCurrencyDataByCode(to);
            return value.divide(targetCurrency.getRates().get(0).getBuyRate(), MathContext.DECIMAL32);
        }
    }
}
