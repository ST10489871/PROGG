/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import org.json.*;



 
/**   
 *
 * @author RC_Student_Lab
 */
public class ChatApp {  
        
    public static Login loginSystem = new Login();

    public static String registerUser(String firstName, String lastName, String username, String password, String phone) {
        // Registration logic
        return loginSystem.registerUser(username, password, phone, firstName, lastName);
    }

    public static String loginUser(String username, String password) {
        // Login logic
        return loginSystem.loginUser(username, password);
    }

    
    public static void main(String[] args) {
        //Login loginSystem = new Login();

        JOptionPane.showMessageDialog(null, "=== Welcome to ChatApp ===");

        // User Registration (as per part 1)
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");

        // Username
        String username = JOptionPane.showInputDialog("Enter Username (must contain '_' and be at least 5 characters):");
        while (!loginSystem.isValidUsername(username)) {
            JOptionPane.showMessageDialog(null, "Username incorrectly formatted.\nIt must contain '_' and be at least 5 characters.");
            username = JOptionPane.showInputDialog("Enter Username:");
        }

        // Password
        String password = JOptionPane.showInputDialog("Enter Password (8+ chars, uppercase, number, special char):");
        while (!loginSystem.isValidPassword(password)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters, include an uppercase letter, a number, and a special character.");
            password = JOptionPane.showInputDialog("Enter Password:");
        }

        // Phone
        String phone = JOptionPane.showInputDialog("Enter Phone Number (+27 followed by 9 digits):");
        while (!loginSystem.isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(null, "Cell number incorrectly formatted.\nMust include international code (+27) followed by 9 digits.");
            phone = JOptionPane.showInputDialog("Enter Phone Number:");
        }

        // Register the user
        String registerResult = loginSystem.registerUser(username, password, phone, firstName, lastName);
        JOptionPane.showMessageDialog(null, registerResult);

        // User Login
        String loginUsername = JOptionPane.showInputDialog("Enter Username to Login:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password:");

        // Check login against stored user
        String loginResult = loginSystem.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, loginResult);

        if (loginResult.startsWith("Welcome")) {  // If login is successful
            // Welcome message
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

            // Main Menu loop
            boolean running = true;
            Scanner scanner = new Scanner(System.in);  // Move the Scanner here

            while (running) {
                // Display the menu options
                System.out.println("Select an option:");
                System.out.println("1. Send Message");
                System.out.println("2. Show Recently Sent Messages (Coming Soon)");
                System.out.println("3. Quit");

                // Get user's choice
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> {
                        // Ask how many messages they want to send
                        System.out.println("How many messages do you want to send?");
                        int numMessages = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        // Loop through the messages
                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("Enter recipient phone number (e.g., +27XXXXXXXXX):");
                            String recipient = scanner.nextLine();

                            System.out.println("Enter the message content:");
                            String messageContent = scanner.nextLine();

                            // Create a new Message object
                            Message message = new Message(recipient, messageContent);

                            // Check Message ID and Recipient validity
                            if (!message.checkMessageId()) {
                                System.out.println("Error: Message ID is not valid.");
                                continue;
                            }
                            if (!message.checkReceiptCell()) {
                                System.out.println("Error: Recipient number is invalid.");
                                continue;
                            }

                            // Display message details and prompt the user for action
                            System.out.println("Message to be sent:");
                            System.out.println("Message ID: " + message.messageID);
                            System.out.println("Recipient: " + message.recipient);
                            System.out.println("Message: " + message.messageContent);
                            System.out.println("Message Hash: " + message.messageHash);

                            String action = message.sentMessage();  // Call sentMessage to allow user choice
                            System.out.println(action);
                        }
                    }

                    case 2 -> // Show Recently Sent Messages (for now, print "Coming Soon")
                        System.out.println("Coming Soon");

                    case 3 -> {
                        // Quit the application
                        System.out.println("Goodbye!");
                        running = false;
                    }

                    default -> System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        }
                // Main app logic, only calling the helper methods
        registerResult = registerUser("John", "Doe", "_JohnDoe", "Password123@", "+27123456789");
        System.out.println(registerResult);

        loginResult = loginUser("_JohnDoe", "Password123@");
        System.out.println(loginResult);
    }
}

class Message {
    // Attributes
    String messageID;
    String recipient;
    String messageContent;
    String messageHash;
    private static final List<Message> messageList = new ArrayList<>();

    // Constructor
    public Message(String recipient, String messageContent) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageContent = messageContent.length() <= 250 ? messageContent : messageContent.substring(0, 250);
        this.messageHash = createMessageHash();
    }

    // Generate a unique 10-digit Message ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder messageID = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            messageID.append(rand.nextInt(10));
        }
        return messageID.toString();
    }

    // Check if the Message ID is exactly 10 characters
    public boolean checkMessageId() {
        return this.messageID.length() == 10;
    }

    // Check if the recipient number is valid: must start with +27 and have exactly 12 characters
    public boolean checkReceiptCell() {
        return this.recipient.startsWith("+27") && this.recipient.length() == 12;
    }

    // Create a message hash
    private String createMessageHash() {
        String[] words = this.messageContent.split(" ");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "DEFAULT";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : "DEFAULT";
        return messageID.substring(0, 2) + ":" + firstWord + ":" + lastWord;
    }

    // Allow user to choose whether to send, store, or discard the message
    public String sentMessage() {
        String action = "";
        Scanner scanner = new Scanner(System.in);  // Using the same Scanner from the main method
        System.out.println("Do you want to send, store or discard this message?");
        System.out.println("1. Send  2. Store  3. Discard");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                action = "Message Sent";
                storeMessage();
            }
            case 2 -> action = "Message Stored";
            case 3 -> action = "Message Discarded";
            default -> action = "Invalid Choice";
        }
        return action;
    }

    // Store the message in the static list and persist it in JSON file
    private void storeMessage() {
        messageList.add(this);
        saveMessagesToJSON();
    }

    // Save messages to a JSON file
    private void saveMessagesToJSON() {
        JSONArray jsonArray = new JSONArray();
        for (Message msg : messageList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageID", msg.messageID);
            jsonObject.put("recipient", msg.recipient);
            jsonObject.put("messageContent", msg.messageContent);
            jsonObject.put("messageHash", msg.messageHash);
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(jsonArray.toString());
            System.out.println("Messages saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // Return the total number of messages sent
    public static int returnTotalMessages() {
        return messageList.size();
    }
}
