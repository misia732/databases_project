package com.database_project.GUI;

import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.DAO.OrderDAOImpl;
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
        
        summaryFrame = new JFrame("Welcome");
        summaryFrame.setSize(400, 200);
        summaryFrame.setLayout(new FlowLayout());

        JButton signInButton = new JButton("Sign up");
        signInButton.addActionListener(e -> displaySignInWindow());
        summaryFrame.add(signInButton);

        JButton logInButton = new JButton("Log in");
        logInButton.addActionListener(e -> displayLogInWindow());
        summaryFrame.add(logInButton);

        summaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        summaryFrame.setVisible(true);
    }

    
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
    
        // Button for Sign-In form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitEvent -> {
            
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String birthDateString = birthDateField.getText(); 
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();
            String password = new String(passwordField.getPassword()); 
            int pizzaCount = 0;  

            
            java.sql.Date sqlBirthDate = null;
            try {
                sqlBirthDate = java.sql.Date.valueOf(birthDateString); 
            } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(signInFrame, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            
            Customer newCustomer = new Customer(firstName, lastName, gender, sqlBirthDate, phoneNumber, email, password, address, postalCode, city, pizzaCount);

        
            try (Connection conn = DatabaseConfig.getConnection()) {
            CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
            customerDAO.insert(newCustomer); 
            loggedInCustomer = newCustomer;

            } catch (SQLException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(signInFrame, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        
            new PizzaOrderingApp();
            signInFrame.dispose();
                    });
                    signInFrame.add(submitButton);
                
                    signInFrame.setVisible(true);
                }
    

 
    private void displayLogInWindow() {
        JFrame logInFrame = new JFrame("Log In");
        logInFrame.setSize(800, 200);
        logInFrame.setLayout(new GridLayout(0, 2));  

        logInFrame.add(new JLabel("Email address:"));
        JTextField emailField = new JTextField();
        logInFrame.add(emailField);

        logInFrame.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        logInFrame.add(passwordField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(submitEvent -> {
            
            try (Connection conn = DatabaseConfig.getConnection()) {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
                String email = emailField.getText();
                String password = new String(passwordField.getPassword()); 
        
                // Check if the email is "admin"
                if (email.equals("admin")) {
                    // Find admin user by email
                    Customer adminCustomer = customerDAO.findByEmail(email);
                    if (adminCustomer != null && adminCustomer.getPassword().equals(password)) {
                        // If admin email and password are correct, open RestaurantApp
                        new AdminScreen();
                        logInFrame.dispose();
                    } else {
                        // Invalid admin credentials
                        JOptionPane.showMessageDialog(logInFrame, "Invalid admin email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // For regular customers
                    Customer customer = customerDAO.findByEmail(email); // Find customer by email
                    if (customer != null && customer.getPassword().equals(password)) {
                        System.out.println("Customer found");
                        loggedInCustomer = customer;
        
                        OrderDAOImpl orderDAOImpl = new OrderDAOImpl(conn);
        
                        // Check if the customer has any undelivered orders
                        if (orderDAOImpl.hasPendingOrder(customer.getID())) {
                            // Open Order Tracking window
                            new OrderTracking(customer.getID(), conn); // Corrected line to instantiate OrderTrackingWindow
                        } else {
                            new PizzaOrderingApp(); 
                        }
                        logInFrame.dispose(); 
                    } else {
                        // Invalid customer credentials
                        JOptionPane.showMessageDialog(logInFrame, "Invalid email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(logInFrame, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        

        logInFrame.add(submitButton);


        logInFrame.setVisible(true);
    }

    public static void main(String[] args) {
       
        new FirstScreen();
    }
}
