package com.currencyapp.controllers;

import com.currencyapp.client.NbpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private NbpClient nbpClient;

    public AccountController(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }
}
