package com.example.verantwoording.rest;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testGetUserEmails() {
        UserService service = new UserService();
        List<String> emails = service.getUserEmails();

        assertNotNull(emails);
        assertFalse(emails.isEmpty(), "Emails list mag niet leeg zijn");
        assertTrue(emails.contains("user1@example.com"));
    }


}
