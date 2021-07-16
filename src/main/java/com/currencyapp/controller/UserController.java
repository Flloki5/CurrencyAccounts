package com.currencyapp.controller;

import com.currencyapp.client.NbpClient;
import com.currencyapp.exception.AccountNotFoundException;
import com.currencyapp.model.User;
import com.currencyapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final NbpClient nbpClient;
    private final UserService userService;

    public UserController(NbpClient nbpClient, UserService userService) {
        this.nbpClient = nbpClient;
        this.userService = userService;
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
    public void createUser(@RequestBody User user){
        userService.create(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
