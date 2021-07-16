package com.currencyapp.controllers;

import com.currencyapp.client.NbpClient;
import com.currencyapp.models.Currency;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

    private final NbpClient nbpClient;

    public CurrencyController(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    @GetMapping(value = "/{code}")
    @ResponseBody
    public Currency getCurrency(@PathVariable String code) throws URISyntaxException, IOException, InterruptedException {
        return nbpClient.getNbpCurrency(code);
    }
}
