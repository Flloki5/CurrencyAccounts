package com.currencyapp.controller;

import com.currencyapp.exception.AccountNotFoundException;
import com.currencyapp.model.User;
import com.currencyapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CurrencyController currencyController;
    private final UserService userService;

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
    public User getUserByPesel(@PathVariable String pesel) throws AccountNotFoundException {
        return userService.getByPesel(pesel);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void saveUser(@RequestBody User user){
        userService.create(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping(value = "/{pesel}/{from}/{to}/{amount}")
    @ResponseBody
    public void convertBetweenSubaccounts(@PathVariable String pesel,
                                          @PathVariable String from,
                                          @PathVariable String to,
                                          @PathVariable BigDecimal amount) throws URISyntaxException, IOException, InterruptedException, AccountNotFoundException {
        BigDecimal convertedAmount = currencyController.calculateExchange(amount, from, to);
        User user = getUserByPesel(pesel);
        if(userService.isEnoughAmount(user, amount, from)){
            userService.updateSubaccountsAmount(user, from, to, amount, convertedAmount);
        }else{
            System.out.println("Za mało środków na koncie");
        }


    }
}
