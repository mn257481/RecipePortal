package com.recipeportal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountProfilePictureTests {

    private AccountService accountService;
    private Account account;

    @BeforeEach
    public void setupAccount() {
        accountService = new AccountService();
        account = new Account("user@recipe.com", "password123");
    }

    @Test
    public void shouldUpdateProfilePictureSuccessfully() {
        boolean result = accountService.updateProfilePicture(account, "avatar.png");

        assertTrue(result, "Zdjęcie powinno zostać zaktualizowane");
        assertEquals("avatar.png", account.getProfilePictureUrl());
    }

    @Test
    public void shouldFailWhenPictureExtensionIsInvalid() {
        boolean result = accountService.updateProfilePicture(account, "not-an-image.txt");

        assertFalse(result, "Aktualizacja powinna się nie udać dla pliku .txt");
        assertNull(account.getProfilePictureUrl());
    }
}