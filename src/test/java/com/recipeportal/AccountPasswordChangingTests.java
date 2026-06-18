package com.recipeportal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountPasswordChangingTests {

    @Test
    public void shouldChangePasswordSuccessfully() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "oldPassword123");

        boolean result = accountService.changePassword(account, "oldPassword123", "newSecretPassword123");

        assertTrue(result, "Hasło powinno zostać pomyślnie zmienione");
        assertEquals("newSecretPassword123", account.getPassword(), "Nowe hasło powinno być zapisane w obiekcie");
    }

    @Test
    public void shouldFailWhenOldPasswordIsIncorrect() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "oldPassword123");

        boolean result = accountService.changePassword(account, "wrongOldPassword", "newSecretPassword123");

        assertFalse(result, "Zmiana powinna się nie udać z powodu błędnego starego hasła");
        assertEquals("oldPassword123", account.getPassword(), "Hasło nie powinno ulec zmianie");
    }
}