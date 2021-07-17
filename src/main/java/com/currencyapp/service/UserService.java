package com.currencyapp.service;

import com.currencyapp.exception.AccountNotFoundException;
import com.currencyapp.model.User;
import com.currencyapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user){
        if(isUserExist(user) && isOverEighteen(user)){
            userRepository.save(user);
        }
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User getByPesel(Long pesel) throws AccountNotFoundException {
        return userRepository.findByPesel(pesel)
                .orElseThrow(() -> new AccountNotFoundException("Account with pesel: " + pesel + " do not exist."));

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void updateSubaccountsAmount(User user, String from, String to, float amount, float convertedAmount){
        float amountUSD = user.getAmountUSD();
        float amountPLN = user.getAmountPLN();

        if(from.equalsIgnoreCase("USD")){
            user.setAmountUSD(amountUSD - amount);
            user.setAmountPLN(amountPLN + convertedAmount);
        }else if(to.equalsIgnoreCase("USD")){
            user.setAmountUSD(amountUSD + convertedAmount);
            user.setAmountPLN(amountPLN - amount);
        }

        userRepository.save(user);
    }

    public boolean isUserExist(User user){
        return userRepository.findByPesel(user.getPesel()).isEmpty();
    }

    public boolean isOverEighteen(User user){
        String sPesel = Long.toString(user.getPesel());
        return convertPeselToAge(sPesel) >= 18;
    }

    public int convertPeselToAge(String pesel){
        Calendar c = Calendar.getInstance();
        LocalDate actualDate = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));

        int birthYear;
        int birthMonth = Integer.parseInt(pesel.substring(2, 4));

        if(birthMonth > 20){
            birthYear = Integer.parseInt("20" + pesel.substring(0, 2));
            birthMonth = birthMonth - 20;
        }else{
            birthYear = Integer.parseInt("19" + pesel.substring(0, 2));
        }
        int birthDay = Integer.parseInt(pesel.substring(4, 6));
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        return Period.between(birthDate, actualDate).getYears();
    }

    public boolean isEnoughAmount(User user, float amount, String code){
        if(code.equalsIgnoreCase("USD")){
            return user.getAmountUSD() >= amount;
        }else{
            return user.getAmountPLN() >= amount;
        }
    }
}
