package com.currencyapp.service;

import com.currencyapp.model.User;
import com.currencyapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private User testUser;

    @Mock
    private UserRepository myRepository;


    @BeforeEach
    public void beforeEachTestMethod(){
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(myRepository);
        this.testUser = new User("name", "surname", "97051035619", new BigDecimal(10), new BigDecimal(5));
    }

    @Test
    public void isOverEighteenShouldReturnTrue(){
        boolean expected = userService.isOverEighteen("97051035619");

        assertThat(true).isEqualTo(expected);
    }

    @Test
    public void isOverEighteenShouldReturnFalse(){
        boolean expected = userService.isOverEighteen("20251035619");

        assertThat(false).isEqualTo(expected);
    }

    @Test
    public void isPeselValidTest(){
        String pesel = "97051035619";
        boolean actualValue1 = userService.isPeselValid(pesel);

        assertAll(
                () -> assertTrue(actualValue1),
                () -> assertEquals(11, pesel.length()));
    }

    @Test
    public void isEnoughAmountTest(){

        boolean expected1 = userService.isEnoughAmount(testUser, new BigDecimal(20), "PLN");
        boolean expected2 = userService.isEnoughAmount(testUser, new BigDecimal(4), "USD");

        assertAll(
                () -> assertFalse(expected1),
                () -> assertTrue(expected2));
    }

    @Test
    public void updateSubaccountsAmountTest(){
        userService.updateSubaccountsAmount(testUser, "PLN", "USD", new BigDecimal(4), new BigDecimal(2));

        assertAll(
                () -> assertEquals(new BigDecimal(6), testUser.getAmountPLN()),
                () -> assertEquals(new BigDecimal(7), testUser.getAmountUSD()));
    }
}
