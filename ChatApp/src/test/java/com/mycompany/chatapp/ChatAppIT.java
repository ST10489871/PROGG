package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatAppIT {

    // Test successful user registration and login
    @Test
    public void testUserRegistrationAndLogin() {
        String registerResult = ChatApp.registerUser("John", "Doe", "_JohnDoe", "Password123@", "+27123456789");
        assertEquals("User successfully registered!", registerResult, "Registration failed");

        String loginResult = ChatApp.loginUser("_JohnDoe", "Password123@");
        assertEquals("Welcome John Doe, great to see you again!", loginResult, "Login failed");
    }

    // Test invalid username during registration
    @Test
    public void testInvalidUsername() {
        String registerResult = ChatApp.registerUser("John", "Doe", "JohnDoe", "Password123@", "+27123456789");
        assertEquals("Username incorrectly formatted (must contain '_' and be at least 5 characters).", registerResult, "Invalid username passed");
    }

    // Test invalid password during registration
    @Test
    public void testInvalidPassword() {
        String registerResult = ChatApp.registerUser("John", "Doe", "_JohnDoe", "short", "+27123456789");
        assertEquals("Password must be at least 8 characters, include an uppercase letter, a number, and a special character.", registerResult, "Invalid password passed");
    }

    // Test invalid phone number during registration
    @Test
    public void testInvalidPhoneNumber() {
        String registerResult = ChatApp.registerUser("John", "Doe", "_JohnDoe", "Password123@", "123456789");
        assertEquals("Cell number incorrectly formatted. Must include international code (+27) followed by 9 digits.", registerResult, "Invalid phone number passed");
    }

    // Test login with incorrect credentials
    @Test
    public void testLoginWithIncorrectCredentials() {
        // First, register the user
        ChatApp.registerUser("John", "Doe", "_JohnDoe", "Password123@", "+27123456789");

        // Attempt to log in with incorrect password
        String loginResult = ChatApp.loginUser("_JohnDoe", "WrongPassword");
        assertEquals("Username or password incorrect, please try again.", loginResult, "Login succeeded with wrong password");

        // Attempt to log in with incorrect username
        loginResult = ChatApp.loginUser("WrongUsername", "Password123@");
        assertEquals("Username or password incorrect, please try again.", loginResult, "Login succeeded with wrong username");
    }
}
