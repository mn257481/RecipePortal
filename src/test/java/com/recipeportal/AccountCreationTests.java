package com.recipeportal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountCreationTests {

    @Test
    public void shouldCreateAccountSuccessfully() {
        AccountService accountService = new AccountService();
        boolean result = accountService.createAccount("test@recipe.com", "validPassword123");
        assertTrue(result);
    }

    @Test
    public void shouldFailWhenPasswordIsTooShort() {
        AccountService accountService = new AccountService();
        boolean result = accountService.createAccount("user@recipe.com", "short");
        assertFalse(result);
    }

    @Test
    public void shouldFailWhenEmailIsNull() {
        AccountService accountService = new AccountService();
        boolean result = accountService.createAccount(null, "validPassword123");
        assertFalse(result);
    }
}