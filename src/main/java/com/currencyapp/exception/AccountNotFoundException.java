package com.currencyapp.exception;

import javassist.NotFoundException;

public class AccountNotFoundException extends NotFoundException {

    public AccountNotFoundException(Long pesel) {
        super("Account" + pesel);
    }
}
