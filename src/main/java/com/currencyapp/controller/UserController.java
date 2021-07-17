package com.currencyapp.controller;

import com.currencyapp.client.NbpClient;
import com.currencyapp.exception.AccountNotFoundException;
import com.currencyapp.model.User;
import com.currencyapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

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
    public User getUserByPesel(@PathVariable Long pesel) throws AccountNotFoundException {
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
    public void convertBetweenSubaccounts(@PathVariable Long pesel,
                                          @PathVariable String from,
                                          @PathVariable String to,
                                          @PathVariable Float amount) throws URISyntaxException, IOException, InterruptedException, AccountNotFoundException {
        float convertedAmount = currencyController.calculateExchange(amount, from, to);
        User user = getUserByPesel(pesel);
        userService.updateSubaccountsAmount(user, from, to, amount, convertedAmount);

    }
}
