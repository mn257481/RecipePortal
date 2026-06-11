package com.recipeportal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class AccountCreationStoryTests {

    private final String VALID_TEST_USERNAME = "SomeUsername";
    private final String VALID_TEST_PASSWORD = "SomeSecurePassword123!";
    private final String VALID_TEST_EMAIL = "someEmail@test.com";

    AccountManager manager;
    AccountCreationSession session;

    @BeforeEach
    AccountCreationSession prepareAccountCreationSession() {
        manager = new AccountManager();
        session = manager.startNewAccountCreationSession();
        session.setUsername(VALID_TEST_USERNAME);
    }

    @Test
    void AccountCreationSuccess() {
        AccountCreationSession session = prepareAccountCreationSession();

        session.setPassword(VALID_TEST_PASSWORD);
        String emailVerificationToken = session.startEmailVerification(VALID_TEST_EMAIL);

        // Wait before user clicks the email (the code here would normally be retrieved from the URL clicked)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        AccountCreationSession session1 = manager.getSessionByEmailCode(emailVerificationToken);
        Assertions.assertSame(session, session1);

        Assertions.assertDoesNotThrow(() -> {
            session1.verifyEmail(emailVerificationToken);

            Account newAccount = session1.finalizeSession();
            Assertions.assertNotNull(newAccount);
        });
    }

    @Test
    void AccountCreationInsecurePasswordFail() {
        Assertions.assertThrows(InvalidPasswordException, () -> {
            session.setPassword("abc");
        });
    }

    @Test
    void AccountCreationInvalidEmailFail() {
        Assertions.assertThrows(InvalidEmailException, () -> {
            session.setPassword("abc");
        });
    }

    @Test
    void AccountCreationTimedOutEmailVerification() {
        session.setPassword(VALID_TEST_PASSWORD);
        String emailVerificationToken = session.startEmailVerification("someEmail@test.com", 1000, TimeUnit.MILLISECONDS); // Set a lower timeout to be able to test quickly

        // Wait before user clicks the email (the code here would normally be retrieved from the URL clicked)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        AccountCreationSession session1 = manager.getSessionByEmailCode(emailVerificationToken);
        Assertions.assertNull(session1);
    }

}
