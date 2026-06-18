package com.recipeportal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountCreationTests {

    private AccountService accountService;

    @BeforeEach
    public void setupAccountService() {
        accountService = new AccountService();
    }

    @Test
    public void shouldCreateAccountSuccessfully() {
        boolean result = accountService.createAccount("test@recipe.com", "validPassword123");
        assertTrue(result);
    }

    @Test
    public void shouldFailWhenPasswordIsTooShort() {
        boolean result = accountService.createAccount("user@recipe.com", "short");
        assertFalse(result);
    }

    @Test
    public void shouldFailWhenEmailIsNull() {
        boolean result = accountService.createAccount(null, "validPassword123");
        assertFalse(result);
    }
}