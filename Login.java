/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_Lab
 */
class Login {
 


    // Variables to store last registered user
    private String registeredUsername;
    private String registeredPassword;
    private String registeredFirstName;
    private String registeredLastName;

    // Username must contain "_" and be at least 5 characters
    public boolean isValidUsername(String username) {
        return username.contains("_") && username.length() >= 5;
    }

    // Password must be 8+ chars, uppercase, digit, special char
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>?/]).{8,}$";
        return password.matches(regex);
    }

    // Phone number must start with +27 and have exactly 9 digits
    public boolean isValidPhoneNumber(String cellNumber) {
        String regex = "^\\+27\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellNumber);
        return matcher.matches();
    }

    // Register user (store in variables)
    public String registerUser(String username, String password, String cellNumber,
                               String firstName, String lastName) {

        if (!isValidUsername(username)) {
            return "Username incorrectly formatted (must contain '_' and be at least 5 characters).";
        }
        if (!isValidPassword(password)) {
            return "Password must be at least 8 characters, include an uppercase letter, a number, and a special character.";
        }
        if (!isValidPhoneNumber(cellNumber)) {
            return "Cell number incorrectly formatted. Must include international code (+27) followed by 9 digits.";
        }

        // Save user data
        registeredUsername = username;
        registeredPassword = password;
        registeredFirstName = firstName;
        registeredLastName = lastName;

        return "User successfully registered!";
    }

    // Login check
    public String loginUser(String username, String password) {
        if (username != null && password != null &&
            username.equals(registeredUsername) &&
            password.equals(registeredPassword)) {
            return "Welcome " + registeredFirstName + " " + registeredLastName + ", great to see you again!";
        }
        return "Username or password incorrect, please try again.";
    }
}
   

