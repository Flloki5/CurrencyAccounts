package com.currencyapp.service;

import com.currencyapp.exception.AccountNotFoundException;
import com.currencyapp.model.User;
import com.currencyapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user){
        userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User getByPesel(Long pesel) throws AccountNotFoundException {
        return userRepository.findByPesel(pesel)
                .orElseThrow(() -> new AccountNotFoundException(pesel));

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}