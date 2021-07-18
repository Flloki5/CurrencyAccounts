package com.currencyapp.controller;

import com.currencyapp.client.NbpClient;
import com.currencyapp.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api/v1/currency")
public class CurrencyController {

    private final NbpClient nbpClient;

    public CurrencyController(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    @GetMapping(value = "/{code}")
    @ResponseBody
    public Currency getCurrencyDataByCode(@PathVariable String code) throws URISyntaxException, IOException, InterruptedException {
        return nbpClient.getNbpCurrency(code);
    }

    @GetMapping(value = "/exchange/{from}/{to}/{value}")
    @ResponseBody
    public BigDecimal calculateExchange(@PathVariable BigDecimal value,
                                   @PathVariable String from,
                                   @PathVariable String to) throws URISyntaxException, IOException, InterruptedException {

        if(to.equalsIgnoreCase("PLN")){
            Currency targetCurrency = getCurrencyDataByCode(from);
            return value.multiply(targetCurrency.getRates().get(0).getSellRate());
        }else{
            Currency targetCurrency = getCurrencyDataByCode(to);
            return value.divide(targetCurrency.getRates().get(0).getBuyRate(), MathContext.DECIMAL32);
        }
    }
}
