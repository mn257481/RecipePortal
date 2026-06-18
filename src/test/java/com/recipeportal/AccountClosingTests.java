package com.recipeportal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountClosingTests {

    @Test
    public void shouldCloseAccountSuccessfully() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "password123");

        boolean result = accountService.closeAccount(account);

        assertTrue(result);
        assertFalse(account.isActive());
    }

    @Test
    public void shouldFailWhenClosingAlreadyClosedAccount() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "password123");

        accountService.closeAccount(account);
        boolean result = accountService.closeAccount(account);

        assertFalse(result);
    }
}