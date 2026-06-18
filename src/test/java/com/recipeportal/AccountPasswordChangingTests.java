package com.recipeportal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class AccountPasswordChangingTests {

    private final String VALID_TEST_USERNAME = "SomeUsername";
    private final String VALID_TEST_PASSWORD = "SomeSecurePassword123!";
    private final String VALID_TEST_NEW_PASSWORD = "AnotherSecurePassword456@";
    private final String VALID_TEST_EMAIL = "someEmail@test.com";

    AccountManager manager;
    Account account;
    PasswordChangeSession session;

    @BeforeEach
    void preparePasswordChangeSession() {
        manager = new AccountManager();

        // Zakładamy, że konto istnieje przed testem
        account = manager.getAccountByUsername(VALID_TEST_USERNAME);
        session = manager.startNewPasswordChangeSession(account);
    }

    @Test
    void PasswordChangeSuccess() {
        session.setNewPassword(VALID_TEST_NEW_PASSWORD);
        String emailVerificationToken = session.startEmailVerification(VALID_TEST_EMAIL);

        // Symulacja opóźnienia przed kliknięciem linku z e-maila
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        PasswordChangeSession session1 = manager.getPasswordChangeSessionByEmailCode(emailVerificationToken);
        Assertions.assertSame(session, session1);

        Assertions.assertDoesNotThrow(() -> {
            session1.verifyEmail(emailVerificationToken);

            Account updatedAccount = session1.finalizeSession();
            Assertions.assertNotNull(updatedAccount);
            Assertions.assertTrue(updatedAccount.passwordMatches(VALID_TEST_NEW_PASSWORD));
        });
    }

    @Test
    void PasswordChangeInsecureNewPasswordFail() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            session.setNewPassword("abc");
        });
    }

    @Test
    void PasswordChangeSameAsOldPasswordFail() {
        Assertions.assertThrows(PasswordSameAsOldException.class, () -> {
            session.setNewPassword(VALID_TEST_PASSWORD);
        });
    }

    @Test
    void PasswordChangeInvalidEmailFail() {
        session.setNewPassword(VALID_TEST_NEW_PASSWORD);

        Assertions.assertThrows(InvalidEmailException.class, () -> {
            session.startEmailVerification("not-a-valid-email");
        });
    }

    @Test
    void PasswordChangeTimedOutEmailVerification() {
        session.setNewPassword(VALID_TEST_NEW_PASSWORD);
        String emailVerificationToken = session.startEmailVerification(VALID_TEST_EMAIL, 1000, TimeUnit.MILLISECONDS);

        // Symulacja opóźnienia — token powinien wygasnąć
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        PasswordChangeSession expiredSession = manager.getPasswordChangeSessionByEmailCode(emailVerificationToken);
        Assertions.assertNull(expiredSession);
    }

    @Test
    void PasswordChangeSessionNotFoundForInvalidToken() {
        PasswordChangeSession notFoundSession = manager.getPasswordChangeSessionByEmailCode("invalid-token-xyz");
        Assertions.assertNull(notFoundSession);
    }

}