package com.recipeportal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountEmailChangingTests {

    @Test
    public void shouldChangeEmailSuccessfully() {
        AccountService accountService = new AccountService();
        Account account = new Account("old@recipe.com", "password123");

        boolean result = accountService.changeEmail(account, "new@recipe.com");

        assertTrue(result, "Email powinien zostać pomyślnie zmieniony");
        assertEquals("new@recipe.com", account.getEmail(), "Nowe konto powinno mieć zaktualizowany e-mail");
    }

    @Test
    public void shouldFailWhenNewEmailIsInvalid() {
        AccountService accountService = new AccountService();
        Account account = new Account("old@recipe.com", "password123");

        boolean result = accountService.changeEmail(account, "invalid-email-format");

        assertFalse(result, "Zmiana powinna się nie udać z powodu błędnego formatu");
        assertEquals("old@recipe.com", account.getEmail(), "Email nie powinien ulec zmianie");
    }
}