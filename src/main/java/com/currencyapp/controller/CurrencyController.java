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

    @GetMapping(value = "/exchange/{value}/{to}")
    @ResponseBody
    public float calculateExchange(@PathVariable("value") Float value,
                                   @PathVariable("to") String to) throws URISyntaxException, IOException, InterruptedException {

        Currency targetCurrency = getCurrencyDataByCode(to);
//        Currency currentCurrency = getCurrencyDataByCode(from);

        return targetCurrency.getRates().get(0).getMid() * value;
    }
}
