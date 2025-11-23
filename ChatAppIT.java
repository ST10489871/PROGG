package com.mycompany.chatapp;

import java.util.Scanner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ChatAppIT {

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

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

    /**
     * Test of registerUser method, of class ChatApp.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String phone = "";
        String expResult = "";
        String result = ChatApp.registerUser(firstName, lastName, username, password, phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class ChatApp.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String username = "";
        String password = "";
        String expResult = "";
        String result = ChatApp.loginUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class ChatApp.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ChatApp.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessages method, of class ChatApp.
     */
    @Test
    public void testSendMessages() {
        System.out.println("sendMessages");
        Scanner scanner = null;
        ChatApp.sendMessages(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showRecentlySentMessages method, of class ChatApp.
     */
    @Test
    public void testShowRecentlySentMessages() {
        System.out.println("showRecentlySentMessages");
        ChatApp.showRecentlySentMessages();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayAllMessages method, of class ChatApp.
     */
    @Test
    public void testDisplayAllMessages() {
        System.out.println("displayAllMessages");
        ChatApp.displayAllMessages();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayLongestMessage method, of class ChatApp.
     */
    @Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");
        ChatApp.displayLongestMessage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchMessageById method, of class ChatApp.
     */
    @Test
    public void testSearchMessageById() {
        System.out.println("searchMessageById");
        Scanner scanner = null;
        ChatApp.searchMessageById(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchMessagesByRecipient method, of class ChatApp.
     */
    @Test
    public void testSearchMessagesByRecipient() {
        System.out.println("searchMessagesByRecipient");
        Scanner scanner = null;
        ChatApp.searchMessagesByRecipient(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMessageByHash method, of class ChatApp.
     */
    @Test
    public void testDeleteMessageByHash() {
        System.out.println("deleteMessageByHash");
        Scanner scanner = null;
        ChatApp.deleteMessageByHash(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fullReport method, of class ChatApp.
     */
    @Test
    public void testFullReport() {
        System.out.println("fullReport");
        ChatApp.fullReport();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
