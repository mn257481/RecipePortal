package com.recipeportal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountClosingTests {

    private AccountService accountService;
    private Account account;

    @BeforeEach
    public void setupAccount() {
        accountService = new AccountService();
        account = new Account("user@recipe.com", "password123");
    }

    @Test
    public void shouldCloseAccountSuccessfully() {
        boolean result = accountService.closeAccount(account);

        assertTrue(result);
        assertFalse(account.isActive());
    }

    @Test
    public void shouldFailWhenClosingAlreadyClosedAccount() {
        accountService.closeAccount(account);
        boolean result = accountService.closeAccount(account);

        assertFalse(result);
    }
}