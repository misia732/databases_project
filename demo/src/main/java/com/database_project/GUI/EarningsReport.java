package com.database_project.GUI;

import javax.swing.*;
import com.database_project.DAO.CustomerDAOImpl;
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
    private JTextArea resultArea; // For displaying orders
    private JLabel summaryLabel;  // For displaying sum, min, max, avg

    private Connection conn;

    public EarningsReport(Connection conn) {
        this.conn = conn; // Pass connection to use later for queries

        setTitle("Generate Earnings Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Fetch the data from the database
        CustomerDAOImpl customerDAO = new CustomerDAOImpl(conn);
        try {
            // Get distinct postal codes, cities, and genders from the database
            List<String> postalCodes = customerDAO.getDistinctPostalCodes();
            List<String> cities = customerDAO.getDistinctCities();
            List<String> genders = customerDAO.getDistinctGenders();

            // Add an empty option for postal codes and cities so that they can be optional filters
            postalCodes.add(0, ""); // Blank for no filtering
            cities.add(0, ""); // Blank for no filtering

            // Add a placeholder for gender selection
            genders.add(0, "Select Gender"); // Placeholder for no selection

            // Create drop-downs
            postalCodeComboBox = new JComboBox<>(postalCodes.toArray(new String[0]));
            cityComboBox = new JComboBox<>(cities.toArray(new String[0]));
            genderComboBox = new JComboBox<>(genders.toArray(new String[0])); // Gender with placeholder
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Age field
        ageTextField = new JTextField(10);

        // Generate report button
        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> {
            generateReport();
        });

        // Add components to the input panel
        inputPanel.add(new JLabel("Select Postal Code:"));
        inputPanel.add(postalCodeComboBox);
        inputPanel.add(new JLabel("Select City:"));
        inputPanel.add(cityComboBox);
        inputPanel.add(new JLabel("Select Gender:"));
        inputPanel.add(genderComboBox);
        inputPanel.add(new JLabel("Enter Age:"));
        inputPanel.add(ageTextField);
        inputPanel.add(new JLabel("")); // Empty label to fill the grid
        inputPanel.add(generateReportButton);

        // Text area to display results (orders list)
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Label to display the summary (sum, min, max, avg)
        summaryLabel = new JLabel("Summary: N/A");

        // Add components to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(summaryLabel, BorderLayout.SOUTH);

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Generate the earnings report based on selected filters
    private void generateReport() {
        String selectedPostalCode = (String) postalCodeComboBox.getSelectedItem();
        String selectedCity = (String) cityComboBox.getSelectedItem();
        String selectedGender = (String) genderComboBox.getSelectedItem();
        String age = ageTextField.getText();

        // Ensure "Select Gender" is not used as a filter
        if ("Select Gender".equals(selectedGender)) {
            selectedGender = ""; // Set to empty if placeholder is selected
        }

        // Build the filtering criteria
        StringBuilder query = new StringBuilder("SELECT o.order_id, o.total_amount, o.order_date, ");
        query.append("SUM(o.total_amount) OVER() AS total_sum, ");
        query.append("MIN(o.total_amount) OVER() AS min_order, ");
        query.append("MAX(o.total_amount) OVER() AS max_order, ");
        query.append("AVG(o.total_amount) OVER() AS avg_order ");
        query.append("FROM `order` o ");
        query.append("JOIN customer c ON o.customer_id = c.customer_id ");
        query.append("WHERE 1 = 1 ");

        // Add filters dynamically
        if (!selectedPostalCode.isEmpty()) {
            query.append("AND c.postal_code = ? ");
        }
        if (!selectedCity.isEmpty()) {
            query.append("AND c.city = ? ");
        }
        if (!selectedGender.isEmpty()) {
            query.append("AND c.gender = ? ");
        }
        if (!age.isEmpty()) {
            query.append("AND TIMESTAMPDIFF(YEAR, c.birth_date, CURDATE()) = ? ");
        }

        query.append("GROUP BY o.order_id, o.total_amount, o.order_date ");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int index = 1;
            // Set the parameters for the query
            if (!selectedPostalCode.isEmpty()) {
                stmt.setString(index++, selectedPostalCode);
            }
            if (!selectedCity.isEmpty()) {
                stmt.setString(index++, selectedCity);
            }
            if (!selectedGender.isEmpty()) {
                stmt.setString(index++, selectedGender);
            }
            if (!age.isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(age));
            }

            // Execute the query and display the results
            ResultSet rs = stmt.executeQuery();
            StringBuilder results = new StringBuilder();
            double totalSum = 0, minOrder = 0, maxOrder = 0, avgOrder = 0;
            boolean first = true;

            while (rs.next()) {
                // Display each order
                results.append("Order ID: ").append(rs.getInt("order_id"))
                        .append(", Amount: ").append(rs.getDouble("total_amount"))
                        .append(", Date: ").append(rs.getDate("order_date")).append("\n");

                // Capture the summary values from the first row
                if (first) {
                    totalSum = rs.getDouble("total_sum");
                    minOrder = rs.getDouble("min_order");
                    maxOrder = rs.getDouble("max_order");
                    avgOrder = rs.getDouble("avg_order");
                    first = false;
                }
            }

            resultArea.setText(results.toString()); // Display orders
            summaryLabel.setText(String.format("Summary - Total: %.2f, Min: %.2f, Max: %.2f, Avg: %.2f",
                    totalSum, minOrder, maxOrder, avgOrder)); // Display summary

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error executing query: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
