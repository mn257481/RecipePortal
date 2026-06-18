package com.recipeportal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountProfilePictureTests {

    @Test
    public void shouldUpdateProfilePictureSuccessfully() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "password123");

        boolean result = accountService.updateProfilePicture(account, "avatar.png");

        assertTrue(result, "Zdjęcie powinno zostać zaktualizowane");
        assertEquals("avatar.png", account.getProfilePictureUrl());
    }

    @Test
    public void shouldFailWhenPictureExtensionIsInvalid() {
        AccountService accountService = new AccountService();
        Account account = new Account("user@recipe.com", "password123");

        boolean result = accountService.updateProfilePicture(account, "not-an-image.txt");

        assertFalse(result, "Aktualizacja powinna się nie udać dla pliku .txt");
        assertNull(account.getProfilePictureUrl());
    }
}