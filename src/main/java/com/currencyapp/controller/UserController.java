package com.currencyapp.controller;

import com.currencyapp.model.User;
import com.currencyapp.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CurrencyController currencyController;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService, CurrencyController currencyController) {
        this.userService = userService;
        this.currencyController = currencyController;
    }

    @GetMapping
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{pesel}")
    @ResponseBody
    public User getUserByPesel(@PathVariable String pesel) throws NotFoundException {
        return userService.getByPesel(pesel);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void saveUser(@RequestBody User user){
        userService.create(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping(value = "/{pesel}/{amount}/{from}/to/{to}")
    @ResponseBody
    public void convertBetweenSubaccounts(@PathVariable String pesel,
                                          @PathVariable String from,
                                          @PathVariable String to,
                                          @PathVariable BigDecimal amount){
        try {
            BigDecimal convertedAmount = currencyController.calculateExchange(amount, from, to);
            User user = getUserByPesel(pesel);
            if (userService.isEnoughAmount(user, amount, from)) {
                userService.updateSubaccountsAmount(user, from, to, amount, convertedAmount);
            }
        }catch (NotFoundException e){
            log.error("User do not exist in db", e);
        }
    }
}
