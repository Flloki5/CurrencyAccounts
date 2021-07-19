package com.currencyapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User user;

    @BeforeEach
    public void beforeEachTestMethod(){
        this.user = new User("name", "surname", "97051035619", new BigDecimal(10), new BigDecimal(0));
    }

    @Test
    public void getUserNameTest(){
        assertThat("name").isEqualTo(user.getName());
    }
}
