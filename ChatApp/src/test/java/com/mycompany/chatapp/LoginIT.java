/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginIT {

    private Login loginSystem;

    // This method will run before each test to set up a fresh Login object
    @BeforeEach
    void setUp() {
        loginSystem = new Login();  // Create a fresh instance of Login before each test
    }

    // This method will run after each test to clean up
    @AfterEach
    void tearDown() {
        loginSystem = null;  // Clear the Login object after each test
    }

    // Test for isValidUsername method
    @Test
    void testIsValidUsername() {
        assertTrue(loginSystem.isValidUsername("_ValidUsername"));
        assertFalse(loginSystem.isValidUsername("Invalid"));
        assertFalse(loginSystem.isValidUsername("No_Underscore"));
        assertFalse(loginSystem.isValidUsername("_"));
    }

    // Test for isValidPassword method
    @Test
    void testIsValidPassword() {
        assertTrue(loginSystem.isValidPassword("Valid1Password@"));
        assertFalse(loginSystem.isValidPassword("short"));
        assertFalse(loginSystem.isValidPassword("noSpecialChar1"));
        assertFalse(loginSystem.isValidPassword("NoDigits@"));
    }

    // Test for isValidPhoneNumber method
    @Test
    void testIsValidPhoneNumber() {
        assertTrue(loginSystem.isValidPhoneNumber("+27123456789"));
        assertFalse(loginSystem.isValidPhoneNumber("+2712345678"));  // Missing one digit
        assertFalse(loginSystem.isValidPhoneNumber("123456789"));    // Missing country code
        assertFalse(loginSystem.isValidPhoneNumber("+28123456789")); // Wrong country code
    }

    // Test for registerUser method
    @Test
    void testRegisterUser() {
        String result = loginSystem.registerUser("_ValidUser", "Password123@", "+27123456789", "John", "Doe");
        assertEquals("User successfully registered!", result);

        // Test invalid username
        result = loginSystem.registerUser("Invalid", "Password123@", "+27123456789", "John", "Doe");
        assertEquals("Username incorrectly formatted (must contain '_' and be at least 5 characters).", result);

        // Test invalid password
        result = loginSystem.registerUser("_ValidUser", "short", "+27123456789", "John", "Doe");
        assertEquals("Password must be at least 8 characters, include an uppercase letter, a number, and a special character.", result);

        // Test invalid phone number
        result = loginSystem.registerUser("_ValidUser", "Password123@", "123456789", "John", "Doe");
        assertEquals("Cell number incorrectly formatted. Must include international code (+27) followed by 9 digits.", result);
    }

    // Test for loginUser method
    @Test
    void testLoginUser() {
        // First, register a user
        loginSystem.registerUser("_ValidUser", "Password123@", "+27123456789", "John", "Doe");

        // Test valid login
        String result = loginSystem.loginUser("_ValidUser", "Password123@");
        assertEquals("Welcome John Doe, great to see you again!", result);

        // Test invalid login with wrong password
        result = loginSystem.loginUser("_ValidUser", "WrongPassword");
        assertEquals("Username or password incorrect, please try again.", result);

        // Test invalid login with wrong username
        result = loginSystem.loginUser("WrongUser", "Password123@");
        assertEquals("Username or password incorrect, please try again.", result);
    }
}

