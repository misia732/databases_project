package com.database_project.GUI;

import javax.swing.*;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.config.DatabaseConfig;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EarningsReport extends JFrame {

    private JComboBox<String> postalCodeComboBox;
    private JComboBox<String> cityComboBox;
    private JComboBox<String> genderComboBox;
    private JTextField ageTextField;
    private JButton generateReportButton;
    private JTextArea resultArea; 
    private JLabel summaryLabel;  

    private Connection conn;

    public EarningsReport(Connection conn) {
        this.conn = conn; 

        setTitle("Generate Earnings Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Fetch the data from the database
        CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
        try {
            // getting distinct postal codes, cities, and genders from the database
            List<String> postalCodes = customerDAO.getDistinctPostalCodes();
            List<String> cities = customerDAO.getDistinctCities();
            List<String> genders = customerDAO.getDistinctGenders();

            
            postalCodes.add(0, ""); 
            cities.add(0, ""); 
            genders.add(0, "Select Gender"); 

            // Create drop-downs
            postalCodeComboBox = new JComboBox<>(postalCodes.toArray(new String[0]));
            cityComboBox = new JComboBox<>(cities.toArray(new String[0]));
            genderComboBox = new JComboBox<>(genders.toArray(new String[0])); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    
        ageTextField = new JTextField(10);

        // generate report button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> generateReport());


        inputPanel.add(new JLabel("Select Postal Code:"));
        inputPanel.add(postalCodeComboBox);
        inputPanel.add(new JLabel("Select City:"));
        inputPanel.add(cityComboBox);
        inputPanel.add(new JLabel("Select Gender:"));
        inputPanel.add(genderComboBox);
        inputPanel.add(new JLabel("Enter Age:"));
        inputPanel.add(ageTextField);
        inputPanel.add(new JLabel("")); 
        inputPanel.add(generateReportButton);

    
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        summaryLabel = new JLabel("Summary: N/A");

  
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(summaryLabel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void generateReport() {
        String selectedPostalCode = (String) postalCodeComboBox.getSelectedItem();
        String selectedCity = (String) cityComboBox.getSelectedItem();
        String selectedGender = (String) genderComboBox.getSelectedItem();
        String ageText = ageTextField.getText().trim();
    
        try {
            if (conn == null || conn.isClosed()) {
                conn = DatabaseConfig.getConnection(); 
            }
    
            
            StringBuilder query = new StringBuilder(
                "SELECT COUNT(o.ID) AS totalOrders, SUM(o.price) AS totalEarnings, " +
                "MIN(o.price) AS minOrder, MAX(o.price) AS maxOrder, AVG(o.price) AS avgOrder " +
                "FROM customer c " +
                "INNER JOIN `order` o ON c.ID = o.customerID " +
                "WHERE 1=1");
    
            
            if (!selectedPostalCode.isEmpty()) {
                query.append(" AND c.postalCode = ?");
            }
            if (!selectedCity.isEmpty()) {
                query.append(" AND c.city = ?");
            }
            if (!selectedGender.equals("Select Gender")) {
                query.append(" AND c.gender = ?");
            }
            if (!ageText.isEmpty()) {
                
                query.append(" AND YEAR(CURDATE()) - YEAR(c.birthDate) = ?");
            }
    
            try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                int paramIndex = 1;
    
                
                if (!selectedPostalCode.isEmpty()) {
                    stmt.setString(paramIndex++, selectedPostalCode);
                }
                if (!selectedCity.isEmpty()) {
                    stmt.setString(paramIndex++, selectedCity);
                }
                if (!selectedGender.equals("Select Gender")) {
                    stmt.setString(paramIndex++, selectedGender);
                }
                if (!ageText.isEmpty()) {
                    stmt.setInt(paramIndex++, Integer.parseInt(ageText)); 
                }
    
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        
                        int totalOrders = rs.getInt("totalOrders");
                        double totalEarnings = rs.getDouble("totalEarnings");
                        double minOrder = rs.getDouble("minOrder");
                        double maxOrder = rs.getDouble("maxOrder");
                        double avgOrder = rs.getDouble("avgOrder");
    
                        
                        resultArea.setText("Total Orders: " + totalOrders + 
                                           "\nTotal Earnings: " + totalEarnings + 
                                           "\nMin Order: " + minOrder + 
                                           "\nMax Order: " + maxOrder + 
                                           "\nAvg Order: " + avgOrder);
                        summaryLabel.setText("Summary: Total Orders = " + totalOrders + 
                                             ", Total Earnings = " + totalEarnings + 
                                             ", Min = " + minOrder + 
                                             ", Max = " + maxOrder + 
                                             ", Avg = " + avgOrder);
                    } else {
                        resultArea.setText("No orders found for the selected criteria.");
                        summaryLabel.setText("Summary: N/A");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid age format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    


    
}
