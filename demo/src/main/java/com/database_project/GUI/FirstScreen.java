package com.database_project.GUI;

import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.config.DatabaseConfig;
import com.database_project.entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FirstScreen {

    private JFrame summaryFrame;

    public static Customer loggedInCustomer;

    public FirstScreen() {
        // Initialize the main frame
        summaryFrame = new JFrame("Welcome");
        summaryFrame.setSize(400, 200);
        summaryFrame.setLayout(new FlowLayout());

        // Create and add the "Sign In" button
        JButton signInButton = new JButton("Sign up");
        signInButton.addActionListener(e -> displaySignInWindow());
        summaryFrame.add(signInButton);

        // Create and add the "Log In" button
        JButton logInButton = new JButton("Log in");
        logInButton.addActionListener(e -> displayLogInWindow());
        summaryFrame.add(logInButton);

        // Set frame properties
        summaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        summaryFrame.setVisible(true);
    }

    // Method to display the Sign-In window
    private void displaySignInWindow() {
        JFrame signInFrame = new JFrame("Sign Up");
        signInFrame.setSize(800, 600);
        signInFrame.setLayout(new GridLayout(0, 2));  // Dynamic layout
    
        signInFrame.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        signInFrame.add(firstNameField);
    
        signInFrame.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        signInFrame.add(lastNameField);
    
        signInFrame.add(new JLabel("Gender:"));
        String[] genders = {"Select", "Female", "Male"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        signInFrame.add(genderComboBox);
    
        signInFrame.add(new JLabel("Birth Date (YYYY-MM-DD):"));
        JTextField birthDateField = new JTextField();
        signInFrame.add(birthDateField);
    
        signInFrame.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField();
        signInFrame.add(phoneField);
    
        signInFrame.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        signInFrame.add(emailField);
    
        signInFrame.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        signInFrame.add(addressField);
    
        signInFrame.add(new JLabel("Postal Code:"));
        JTextField postalCodeField = new JTextField();
        signInFrame.add(postalCodeField);
    
        signInFrame.add(new JLabel("City:"));
        JTextField cityField = new JTextField();
        signInFrame.add(cityField);
    
        // Add Password Field for Sign-In
        signInFrame.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        signInFrame.add(passwordField);
    
        // Submit Button for Sign-In form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitEvent -> {
            // Collect form data
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String birthDateString = birthDateField.getText(); // Get the birth date string
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();
            String password = new String(passwordField.getPassword()); // Get password
            int pizzaCount = 0;  // Initialize with 0 for new customers

            // Validate and convert the birth date
            java.sql.Date sqlBirthDate = null;
            try {
                sqlBirthDate = java.sql.Date.valueOf(birthDateString); // Try converting to SQL Date
            } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(signInFrame, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if date is invalid
            }

            // Create a new Customer object using the parameterized constructor
            Customer newCustomer = new Customer(firstName, lastName, gender, sqlBirthDate, phoneNumber, email, password, address, postalCode, city, pizzaCount);

            // Insert the customer into the database
            try (Connection conn = DatabaseConfig.getConnection()) {
            CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
            customerDAO.insert(newCustomer); // Insert the new customer
            loggedInCustomer = newCustomer;

            } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
            JOptionPane.showMessageDialog(signInFrame, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }

            // Open the PizzaOrderingApp
            new PizzaOrderingApp();
            signInFrame.dispose(); // Close the sign-in frame
                    });
                    signInFrame.add(submitButton);
                
                    signInFrame.setVisible(true);
                }
    

    // Method to display the Log-In window
    private void displayLogInWindow() {
        JFrame logInFrame = new JFrame("Log In");
        logInFrame.setSize(800, 200);
        logInFrame.setLayout(new GridLayout(0, 2));  // Dynamic layout

        logInFrame.add(new JLabel("Email address:"));
        JTextField emailField = new JTextField();
        logInFrame.add(emailField);

        logInFrame.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        logInFrame.add(passwordField);

        // Submit Button for Log-In form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitEvent -> {
            // Logic to check email and password
            try (Connection conn = DatabaseConfig.getConnection()) {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
                String email = emailField.getText();
                String password = new String(passwordField.getPassword()); // Get password

                Customer customer = customerDAO.findByEmail(email); // Find customer by email

                if (customer != null && customer.getPassword().equals(password)) {
                    System.out.println("Customer found");
                    loggedInCustomer = customer;
                    new PizzaOrderingApp(); // Open the PizzaOrderingApp
                    logInFrame.dispose(); // Close the login frame
                } else {
                    JOptionPane.showMessageDialog(logInFrame, "Invalid email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception
                JOptionPane.showMessageDialog(logInFrame, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        logInFrame.add(submitButton);

        logInFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the application
        new FirstScreen();
    }
}
