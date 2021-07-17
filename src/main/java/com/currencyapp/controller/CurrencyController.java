package com.currencyapp.controller;

import com.currencyapp.client.NbpClient;
import com.currencyapp.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public float calculateExchange(@PathVariable Float value,
                                   @PathVariable String from,
                                   @PathVariable String to) throws URISyntaxException, IOException, InterruptedException {

        if(to.equalsIgnoreCase("PLN")){
            Currency targetCurrency = getCurrencyDataByCode(from);
            return value * targetCurrency.getRates().get(0).getMid();
        }else{
            Currency targetCurrency = getCurrencyDataByCode(to);
            return value / targetCurrency.getRates().get(0).getMid();
        }
    }
}
