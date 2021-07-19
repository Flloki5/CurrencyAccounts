package com.currencyapp.service;

import com.currencyapp.model.User;
import com.currencyapp.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        if (isUserExist(user) && isOverEighteen(user.getPesel()) && isPeselValid(user.getPesel())) {
            userRepository.save(user);
        }
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User getByPesel(String pesel) throws NotFoundException {

        Optional<User> user = userRepository.findByPesel(pesel);
        if(user.isEmpty()){
            throw new NotFoundException("User do not exist");
        }else{
            return user.get();
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateSubaccountsAmount(User user, String from, String to, BigDecimal amount, BigDecimal convertedAmount) {
        BigDecimal amountUSD = user.getAmountUSD();
        BigDecimal amountPLN = user.getAmountPLN();

        if (from.equalsIgnoreCase("USD")) {
            user.setAmountUSD(amountUSD.subtract(amount));
            user.setAmountPLN(amountPLN.add(convertedAmount));
        } else if (to.equalsIgnoreCase("USD")) {
            user.setAmountUSD(amountUSD.add(convertedAmount));
            user.setAmountPLN(amountPLN.subtract(amount));
        }
        userRepository.save(user);
    }

    public boolean isUserExist(User user) {
        return userRepository.findByPesel(user.getPesel()).isEmpty();
    }

    public boolean isOverEighteen(String pesel) {
        return convertPeselToAge(pesel) >= 18;
    }

    public int convertPeselToAge(String pesel) {
        Calendar c = Calendar.getInstance();
        LocalDate actualDate = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));

        int birthYear;
        int birthMonth = Integer.parseInt(pesel.substring(2, 4));

        if (birthMonth > 20) {
            birthYear = Integer.parseInt("20" + pesel.substring(0, 2));
            birthMonth = birthMonth - 20;
        } else {
            birthYear = Integer.parseInt("19" + pesel.substring(0, 2));
        }
        int birthDay = Integer.parseInt(pesel.substring(4, 6));
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        return Period.between(birthDate, actualDate).getYears();
    }

    public boolean isEnoughAmount(User user, BigDecimal demandAmount, String code) {
        BigDecimal actualAmount;

        if (code.equalsIgnoreCase("USD")) {
            actualAmount = user.getAmountUSD();
        } else {
            actualAmount = user.getAmountPLN();
        }

        return actualAmount.compareTo(demandAmount) >= 0;
    }

    public boolean isPeselValid(String pesel){
        return pesel.length() == 11;
    }
}
