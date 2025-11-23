/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import javax.swing.JOptionPane;
import java.util.Scanner;

public class ChatApp {

    public static Login loginSystem = new Login();

    // Part 3 Arrays
    static String[] messageIds = new String[100];
    static String[] messageHashes = new String[100];
    static String[] recipients = new String[100];
    static String[] messageContents = new String[100];
    static String[] messageStatuses = new String[100]; // "Sent", "Stored", "Disregarded"
    static int messageCount = 0;

    public static String registerUser(String firstName, String lastName, String username, String password, String phone) {
        return loginSystem.registerUser(username, password, phone, firstName, lastName);
    }

    public static String loginUser(String username, String password) {
        return loginSystem.loginUser(username, password);
    }

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "=== Welcome to ChatApp ===");

        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");

        String username = JOptionPane.showInputDialog("Enter Username (must contain '_' and be at least 5 characters):");
        while (!loginSystem.isValidUsername(username)) {
            JOptionPane.showMessageDialog(null, "Username incorrectly formatted.\nIt must contain '_' and be at least 5 characters.");
            username = JOptionPane.showInputDialog("Enter Username:");
        }

        String password = JOptionPane.showInputDialog("Enter Password (8+ chars, uppercase, number, special char):");
        while (!loginSystem.isValidPassword(password)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters, include an uppercase letter, a number, and a special character.");
            password = JOptionPane.showInputDialog("Enter Password:");
        }

        String phone = JOptionPane.showInputDialog("Enter Phone Number (+27 followed by 9 digits):");
        while (!loginSystem.isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(null, "Cell number incorrectly formatted.\nMust include international code (+27) followed by 9 digits.");
            phone = JOptionPane.showInputDialog("Enter Phone Number:");
        }

        String registerResult = loginSystem.registerUser(username, password, phone, firstName, lastName);
        JOptionPane.showMessageDialog(null, registerResult);

        String loginUsername = JOptionPane.showInputDialog("Enter Username to Login:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password:");

        String loginResult = loginSystem.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, loginResult);

        if (loginResult.startsWith("Welcome")) {

            JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n=== ChatApp Menu ===");
                System.out.println("1. Send Message");
                System.out.println("2. Show Recently Sent Messages");
                System.out.println("3. Display All Messages");
                System.out.println("4. Display Longest Message");
                System.out.println("5. Search Message by ID");
                System.out.println("6. Search Messages by Recipient");
                System.out.println("7. Delete Message by Hash");
                System.out.println("8. Full Report");
                System.out.println("9. Quit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> sendMessages(scanner);
                    case 2 -> showRecentlySentMessages();
                    case 3 -> displayAllMessages();
                    case 4 -> displayLongestMessage();
                    case 5 -> searchMessageById(scanner);
                    case 6 -> searchMessagesByRecipient(scanner);
                    case 7 -> deleteMessageByHash(scanner);
                    case 8 -> fullReport();
                    case 9 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice, please select again.");
                }
            }
        }
    }

    // Send messages
    public static void sendMessages(Scanner scanner) {
        System.out.print("How many messages do you want to send? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numMessages; i++) {
            System.out.print("Enter recipient phone number (+27XXXXXXXXX): ");
            String recipient = scanner.nextLine();

            System.out.print("Enter message content (max 250 chars): ");
            String content = scanner.nextLine();
            if (content.length() > 250) content = content.substring(0, 250);

            Message msg = new Message(recipient, content);
            System.out.println("Message ID: " + msg.messageID);
            System.out.println("Message Hash: " + msg.messageHash);
            System.out.println("Message: " + msg.messageContent);

            System.out.println("1. Send  2. Store  3. Discard");
            System.out.print("Choose action: ");
            int actionChoice = scanner.nextInt();
            scanner.nextLine();
            String status;
            switch (actionChoice) {
                case 1 -> {
                    status = "Sent";
                    msg.storeMessage();
                }
                case 2 -> status = "Stored";
                case 3 -> status = "Disregarded";
                default -> {
                    status = "Invalid";
                    System.out.println("Invalid choice, message discarded.");
                }
            }
            // Save to arrays
            if (messageCount < 100) {
                messageIds[messageCount] = msg.messageID;
                messageHashes[messageCount] = msg.messageHash;
                recipients[messageCount] = recipient;
                messageContents[messageCount] = content;
                messageStatuses[messageCount] = status;
                messageCount++;
            }
            System.out.println("Message " + status);
        }
    }

    public static void showRecentlySentMessages() {
        System.out.println("\n=== Recently Sent Messages ===");
        boolean any = false;
        for (int i = 0; i < messageCount; i++) {
            if ("Sent".equals(messageStatuses[i])) {
                System.out.println(messageContents[i] + " -> " + recipients[i]);
                any = true;
            }
        }
        if (!any) System.out.println("No sent messages found.");
    }

    public static void displayAllMessages() {
        System.out.println("\n=== All Messages ===");
        for (int i = 0; i < messageCount; i++) {
            System.out.println(i + 1 + ". [" + messageStatuses[i] + "] " + messageContents[i] + " -> " + recipients[i]);
        }
    }

    public static void displayLongestMessage() {
        int maxIndex = -1;
        int maxLen = 0;
        for (int i = 0; i < messageCount; i++) {
            if (messageContents[i] != null && messageContents[i].length() > maxLen) {
                maxLen = messageContents[i].length();
                maxIndex = i;
            }
        }
        if (maxIndex != -1) {
            System.out.println("\nLongest message: " + messageContents[maxIndex] + " -> " + recipients[maxIndex]);
        } else {
            System.out.println("No messages found.");
        }
    }

    public static void searchMessageById(Scanner scanner) {
        System.out.print("Enter Message ID to search: ");
        String id = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < messageCount; i++) {
            if (id.equals(messageIds[i])) {
                System.out.println("Found message: [" + messageStatuses[i] + "] " + messageContents[i] + " -> " + recipients[i]);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Message ID not found.");
    }

    public static void searchMessagesByRecipient(Scanner scanner) {
        System.out.print("Enter recipient phone number to search: ");
        String rec = scanner.nextLine();
        boolean found = false;
        System.out.println("\nMessages for " + rec + ":");
        for (int i = 0; i < messageCount; i++) {
            if (rec.equals(recipients[i])) {
                System.out.println("[" + messageStatuses[i] + "] " + messageContents[i] + " (ID: " + messageIds[i] + ")");
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for this recipient.");
    }

    public static void deleteMessageByHash(Scanner scanner) {
        System.out.print("Enter Message Hash to delete: ");
        String hash = scanner.nextLine();
        boolean deleted = false;
        for (int i = 0; i < messageCount; i++) {
            if (hash.equals(messageHashes[i])) {
                for (int j = i; j < messageCount - 1; j++) {
                    messageIds[j] = messageIds[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    recipients[j] = recipients[j + 1];
                    messageContents[j] = messageContents[j + 1];
                    messageStatuses[j] = messageStatuses[j + 1];
                }
                messageCount--;
                deleted = true;
                System.out.println("Message deleted.");
                break;
            }
        }
        if (!deleted) System.out.println("Message Hash not found.");
    }

    public static void fullReport() {
        System.out.println("\n=== Full Message Report ===");
        System.out.printf("%-15s %-12s %-30s %-12s %-10s%n", "Message Hash", "Message ID", "Content", "Recipient", "Status");
        for (int i = 0; i < messageCount; i++) {
            System.out.printf("%-15s %-12s %-30s %-12s %-10s%n", messageHashes[i], messageIds[i], messageContents[i], recipients[i], messageStatuses[i]);
        }
    }

    // Inner Message class
    static class Message {
        String messageID;
        String recipient;
        String messageContent;
        String messageHash;
        private static final java.util.List<Message> messageList = new java.util.ArrayList<>();

        public Message(String recipient, String messageContent) {
            this.messageID = generateMessageID();
            this.recipient = recipient;
            this.messageContent = messageContent.length() <= 250 ? messageContent : messageContent.substring(0, 250);
            this.messageHash = createMessageHash();
        }

        private String generateMessageID() {
            java.util.Random rand = new java.util.Random();
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < 10; i++) id.append(rand.nextInt(10));
            return id.toString();
        }

        public boolean checkMessageId() { return this.messageID.length() == 10; }

        public boolean checkReceiptCell() { return this.recipient.startsWith("+27") && this.recipient.length() == 12; }

        private String createMessageHash() {
            String[] words = this.messageContent.split(" ");
            String first = words.length > 0 ? words[0].toUpperCase() : "DEFAULT";
            String last = words.length > 1 ? words[words.length - 1].toUpperCase() : "DEFAULT";
            return messageID.substring(0, 2) + ":" + first + ":" + last;
        }

        public void storeMessage() {
            messageList.add(this);
            saveMessagesToJSON();
        }

        private void saveMessagesToJSON() {
            org.json.JSONArray jsonArray = new org.json.JSONArray();
            for (Message msg : messageList) {
                org.json.JSONObject obj = new org.json.JSONObject();
                obj.put("messageID", msg.messageID);
                obj.put("recipient", msg.recipient);
                obj.put("messageContent", msg.messageContent);
                obj.put("messageHash", msg.messageHash);
                jsonArray.put(obj);
            }
            try (java.io.FileWriter file = new java.io.FileWriter("messages.json")) {
                file.write(jsonArray.toString());
            } catch (java.io.IOException e) {
                System.out.println("Error saving messages: " + e.getMessage());
            }
        }
    }
}
