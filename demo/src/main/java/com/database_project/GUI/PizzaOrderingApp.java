package com.database_project.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PizzaOrderingApp extends JFrame {
    private JTextArea orderArea;
    private double totalPrice = 0.0;
    private HashMap<String, Integer> orderedPizzas;

    private static final HashMap<String, Double> ingredientPrices = new HashMap<>();
    private static final Map<String, String[]> pizzaIngredients = new HashMap<>();

    public PizzaOrderingApp() {

        orderedPizzas = new HashMap<>(); // to keep track of pizzas and their quantities

        // Set up the frame
        setTitle("Pizza Ordering System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel (Top Section)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.PINK);
        headerPanel.setPreferredSize(new Dimension(400, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));

        JLabel headerLabel = new JLabel("Welcome to M&M Pizza!", JLabel.CENTER);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Pizza Menu Panel (Middle Section)
        JPanel pizzaMenuPanel = new JPanel();
        pizzaMenuPanel.setBackground(Color.GRAY);
        pizzaMenuPanel.setLayout(new GridLayout(5, 1)); // Adjust layout to 5 rows for the drop-down lists

        // Create an ingredients map

        pizzaIngredients.put("Margherita", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Oregano"
        });
        
        pizzaIngredients.put("Vegan Margherita", new String[]{
            "Pizza dough", "Tomato sauce", "Vegan mozzarella", "Oregano"
        });
        
        pizzaIngredients.put("Pepperoni", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Pepperoni slices"
        });
        
        pizzaIngredients.put("Parma", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Ham", "Rucola"
        });
        
        pizzaIngredients.put("Capricciosa", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Mushrooms", "Ham"
        });
        
        pizzaIngredients.put("Spinach & Feta", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Spinach", "Feta cheese"
        });
        
        pizzaIngredients.put("4 cheese", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Cheddar cheese", "Gorgonzola", "Parmesan"
        });
        
        pizzaIngredients.put("Veggie Surpreme", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Green peppers", "Red peppers", "Mushrooms", "Sweetcorn", "Red onions"
        });
        
        pizzaIngredients.put("Mushroom & Truffle", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Mushrooms", "Truffle oil"
        });
        
        pizzaIngredients.put("Meat Lover's", new String[]{
            "Pizza dough", "Tomato sauce", "Mozzarella cheese", "Pepperoni slices", "Crispy bacon", "Italian sausage", "Ground beef"
        });
        


        // HashMap to store ingredients and their prices

        // Add ingredients and their hypothetical prices
        ingredientPrices.put("Pizza dough", 0.80);
        ingredientPrices.put("Tomato sauce", 0.50);
        ingredientPrices.put("Mozzarella cheese", 1.20);
        ingredientPrices.put("Pepperoni slices", 1.50);
        ingredientPrices.put("Mushrooms", 0.70);
        ingredientPrices.put("Green peppers", 0.40);
        ingredientPrices.put("Red peppers", 0.40);
        ingredientPrices.put("Ham", 1.20);
        ingredientPrices.put("Cheddar cheese", 0.70);
        ingredientPrices.put("Red onions", 0.30);
        ingredientPrices.put("Olive oil", 0.20);
        ingredientPrices.put("Oregano", 0.10);
        ingredientPrices.put("BBQ sauce", 0.60);
        ingredientPrices.put("Crispy bacon", 1.00);
        ingredientPrices.put("Sweetcorn", 0.30);
        ingredientPrices.put("Italian sausage", 1.50);
        ingredientPrices.put("Ground beef", 1.40);
        ingredientPrices.put("Grilled chicken breast", 1.80);
        ingredientPrices.put("Rucola", 0.50);
        ingredientPrices.put("Feta cheese", 1.00);
        ingredientPrices.put("Truffle oil", 1.20);
        ingredientPrices.put("Vegan mozzarella", 1.20);
        ingredientPrices.put("Gorgonzola", 1.20);
        ingredientPrices.put("Parmesan", 0.9);
        ingredientPrices.put("Spinach", 0.5);



        // Calculate the price for each pizza
        double pepperoniPrice = calculatePizzaPrice("Pepperoni");
        double meatLoversPrice = calculatePizzaPrice("Meat Lover's");
        double capricciosaPrice = calculatePizzaPrice("Capricciosa");
        double parmaPrice = calculatePizzaPrice("Parma");
        double margheritaPrice = calculatePizzaPrice("Margherita");
        double spinachFetaPrice = calculatePizzaPrice("Spinach & Feta");
        double fourCheesePrice = calculatePizzaPrice("4 cheese");
        double veggiePrice = calculatePizzaPrice("Veggie Surpreme");
        double mushroomPrice = calculatePizzaPrice("Mushroom & Truffle");
        double veganMargheritaPrice = calculatePizzaPrice("Vegan Margherita");
        

        // Meat Pizzas Dropdown
        JComboBox<String> meatPizzasCombo = new JComboBox<>(new String[]{
                "Pepperoni ($" + String.format("%.2f", pepperoniPrice) + ")",
                "Meat Lover's ($" + String.format("%.2f", meatLoversPrice) + ")",
                "Capricciosa ($" + String.format("%.2f", capricciosaPrice) + ")",
                "Parma ($" + String.format("%.2f", parmaPrice) + ")"
        });
        pizzaMenuPanel.add(createDropdownPanel("Meat Pizzas", meatPizzasCombo));


        // Vegetarian Pizzas Dropdown
        JComboBox<String> vegetarianPizzasCombo = new JComboBox<>(new String[]{
                "Margherita ($" + String.format("%.2f", margheritaPrice) + ")",
                "Spinach & Feta ($" + String.format("%.2f", spinachFetaPrice) + ")",
                "4 cheese ($" + String.format("%.2f", fourCheesePrice) + ")"
        });
        pizzaMenuPanel.add(createDropdownPanel("Vegetarian Pizzas", vegetarianPizzasCombo));

        // Vegan Pizzas Dropdown
        JComboBox<String> veganPizzasCombo = new JComboBox<>(new String[]{
                "Vegan Margherita ($" + String.format("%.2f", veganMargheritaPrice) + ")",
                "Veggie Surpreme ($" + String.format("%.2f", veggiePrice) + ")",
                "Mushroom & Truffle ($" + String.format("%.2f", mushroomPrice) + ")",
        });
        pizzaMenuPanel.add(createDropdownPanel("Vegan Pizzas", veganPizzasCombo));

        // Desserts Dropdown
        JComboBox<String> dessertsCombo = new JComboBox<>(new String[]{
                "Tiramisu ($6.0)",
                "Chocolate Lava Cake ($7.0)",
                "Cheesecake ($6.5)"
        });
        pizzaMenuPanel.add(createDropdownPanel("Desserts", dessertsCombo));

        // Drinks Dropdown
        JComboBox<String> drinksCombo = new JComboBox<>(new String[]{
                "Coca-Cola ($2.5)",
                "Fresh Orange Juice ($3.0)",
                "Water ($1.5)"
        });
        pizzaMenuPanel.add(createDropdownPanel("Drinks", drinksCombo));

        add(pizzaMenuPanel, BorderLayout.CENTER);

        // Order Area (Bottom Section)
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBackground(Color.WHITE);
        JLabel orderLabel = new JLabel("Your Order", JLabel.CENTER);
        orderLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        orderPanel.add(orderLabel, BorderLayout.NORTH);

        orderArea = new JTextArea();
        orderArea.setEditable(false);
        orderArea.setLineWrap(true); // Allow line wrapping
        orderArea.setWrapStyleWord(true); // Wrap on word boundaries

        // Wrap JTextArea in JScrollPane
        JScrollPane scrollPane = new JScrollPane(orderArea);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Adjust height as needed
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Show scroll bar as needed

        orderPanel.add(scrollPane, BorderLayout.CENTER);

        // Order Button
        JButton orderButton = new JButton("ORDER");
        orderButton.addActionListener(e -> {
            if (orderedPizzas.isEmpty()) {
                JOptionPane.showMessageDialog(PizzaOrderingApp.this,
                        "You need to order at least 1 pizza",
                        "Order Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // If there are pizzas in the order, show the order summary
                showOrderSummary();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the orderPanel to the main layout
        add(orderPanel, BorderLayout.SOUTH); // This should be the only addition of orderPanel

    }

    private JPanel createDropdownPanel(String label, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel categoryLabel = new JLabel(label);
        categoryLabel.setFont(new Font("Times New Roman", Font.BOLD, 16)); // Change font and size
        panel.add(categoryLabel, BorderLayout.WEST);

        // Create a panel for the combo box
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(comboBox, BorderLayout.CENTER); // Add combo box to the center
        // Create a panel for buttons aligned to the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setVisible(false); // Initially hidden

        // Add buttons
        JButton addButton = new JButton("+");
        JButton removeButton = new JButton("-");
        JButton showIngredientsButton = new JButton("Show Ingredients");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(showIngredientsButton);

        // Add the button panel to the combo panel on the right
        comboPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(comboPanel, BorderLayout.CENTER);

        // ActionListener to display buttons when a pizza is selected
        comboBox.addActionListener(e -> {
            String selectedPizza = (String) comboBox.getSelectedItem();
            if (selectedPizza != null) {
                buttonPanel.setVisible(true); // Show the buttons when an item is selected
            }
        });

        // Get the pizza name without the price part and set the action listeners for the buttons outside the dropdown selection logic
        addButton.addActionListener(e -> {
            String selectedPizza = (String) comboBox.getSelectedItem();
            if (selectedPizza != null) {
                String pizzaName = selectedPizza.split(" \\(")[0];  // Extract pizza name without price
                double pizzaPrice = calculatePizzaPrice(pizzaName);
                new PizzaOrderAction(pizzaName, pizzaPrice, true).actionPerformed(e);
            }
        });

        removeButton.addActionListener(e -> {
            String selectedPizza = (String) comboBox.getSelectedItem();
            if (selectedPizza != null) {
                String pizzaName = selectedPizza.split(" \\(")[0];  // Extract pizza name without price
                double pizzaPrice = calculatePizzaPrice(pizzaName);
                new PizzaOrderAction(pizzaName, pizzaPrice, false).actionPerformed(e);
            }
        });

        showIngredientsButton.addActionListener(e -> {
            String selectedPizza = (String) comboBox.getSelectedItem();
            if (selectedPizza != null) {
                String pizzaName = selectedPizza.split(" \\(")[0];  // Extract pizza name without price
                new IngredientsAction(pizzaName).actionPerformed(e);
            }
        });

        return panel;
    }

    private class PizzaOrderAction implements ActionListener {
        public String pizzaName;
        public double pizzaPrice;
        public boolean isAdd;


        public PizzaOrderAction(String pizzaName, double pizzaPrice, boolean isAdd) {
            this.pizzaName = pizzaName;
            this.pizzaPrice = pizzaPrice;
            this.isAdd = isAdd;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Manage pizza quantities
            int quantity = orderedPizzas.getOrDefault(pizzaName, 0);

            if (isAdd) {
                // Add pizza to the order
                quantity++;
                orderedPizzas.put(pizzaName, quantity);
                totalPrice += pizzaPrice; // Update total price
                orderArea.append("Added: " + pizzaName + " (x" + quantity + ") - $" + (pizzaPrice * quantity) + "\n");
            } else if (quantity > 0) {
                // Remove pizza from the order if quantity is greater than zero
                quantity--;
                if (quantity == 0) {
                    // If quantity reaches zero, remove the pizza from the ordered list
                    orderedPizzas.remove(pizzaName);
                    totalPrice -= pizzaPrice; // Update total price when pizza is removed
                    orderArea.append("Removed: " + pizzaName + " (x" + quantity + ") - $" + (pizzaPrice * quantity) + "\n");
                } else {
                    // Update the quantity in the order
                    orderedPizzas.put(pizzaName, quantity);
                    totalPrice -= pizzaPrice; // Update total price
                    orderArea.append("Removed: " + pizzaName + " (x" + quantity + ") - $" + (pizzaPrice * quantity) + "\n");
                }
            }

            // Update total price
            orderArea.append("Total: $" + String.format("%.2f", totalPrice) + "\n\n");
        }


    }

    public void updateOrderSummary() {
        // Clear the orderArea before updating
        orderArea.setText("");

        // Display all pizzas in the current order with quantities
        for (Map.Entry<String, Integer> entry : orderedPizzas.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            double totalItemPrice = calculatePizzaPrice(name) * quantity;
            if (totalItemPrice != 0) {
                orderArea.append(name + " (x" + quantity + ") - $" + String.format("%.2f", totalItemPrice) + "\n");
            }
        }

        // Update total price
        orderArea.append("\nTotal: $" + String.format("%.2f", totalPrice) + "\n\n");
    }

    private class IngredientsAction implements ActionListener {
        private String pizzaName;

        public IngredientsAction(String pizzaName) {
            this.pizzaName = pizzaName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Prepare the cost breakdown
            StringBuilder ingredientList = new StringBuilder("Ingredients for " + pizzaName + ":\n");

            double ingredientCost = 0.0;

            // Check if the pizza name exists in the pizzaIngredients map
            if (pizzaIngredients.containsKey(pizzaName)) {
                // Get the ingredients for the pizza
                String[] ingredients = pizzaIngredients.get(pizzaName);

                // Loop through ingredients and add their prices
                for (String ingredient : ingredients) {
                    if (ingredientPrices.containsKey(ingredient)) {
                        double price = ingredientPrices.get(ingredient);
                        ingredientCost += price;
                        ingredientList.append("- ").append(ingredient).append(": $").append(price).append("\n");
                    } else {
                        ingredientList.append("- ").append(ingredient).append(": Not available in the price list.\n");
                    }
                }

                // Calculate profit margin and VAT
                double profitMargin = 0.40 * ingredientCost;
                profitMargin = Math.round(profitMargin * 100.0) / 100.0;
                double priceWithProfit = ingredientCost + profitMargin;
                double vat = 0.09 * priceWithProfit;
                vat = Math.round(vat * 100.0) / 100.0;
                double finalPrice = priceWithProfit + vat;
                finalPrice = Math.round(finalPrice * 100.0) / 100.0;

                // Append cost breakdown to the ingredient list
                ingredientList.append("\nCost breakdown:\n");
                ingredientList.append("Ingredient Cost: $").append(ingredientCost).append("\n");
                ingredientList.append("Profit (40%): $").append(profitMargin).append("\n");
                ingredientList.append("VAT (9%): $").append(vat).append("\n");
                ingredientList.append("Total Price: $").append(finalPrice).append("\n");

            } else {
                ingredientList.append("Pizza not found.");
            }

            // Display ingredients and price breakdown in a dialog
            JOptionPane.showMessageDialog(PizzaOrderingApp.this, ingredientList.toString(), "Ingredients & Price Breakdown", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showOrderSummary() {
        JFrame summaryFrame = new JFrame("Order Summary");
        summaryFrame.setSize(800, 600);
        summaryFrame.setLayout(new GridLayout(0, 1));

        // Summary Label
        JLabel summaryLabel = new JLabel("Summary of Your Order", JLabel.CENTER);
        summaryLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        summaryFrame.add(summaryLabel);

        // Order Details
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        updateOrderSummary();  // Update the order summary
        summaryArea.setText(orderArea.getText());
        summaryFrame.add(new JScrollPane(summaryArea));

        // Sign In Button
        JButton signInButton = new JButton("Sign in");
        signInButton.addActionListener(e -> {
            // Open new window for Sign-In
            JFrame signInFrame = new JFrame("Sign In");
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

            // Submit Button after filling the form
            JButton submitButton = new JButton("Submit Order");
            submitButton.addActionListener(submitEvent -> {
                // Collect and process the form data, and display in order summary or save to database
                StringBuilder orderData = new StringBuilder("Order Details:\n");
                orderData.append(summaryArea.getText()).append("\n");
                orderData.append("Customer Details:\n");
                orderData.append("First Name: ").append(firstNameField.getText()).append("\n");
                orderData.append("Last Name: ").append(lastNameField.getText()).append("\n");
                orderData.append("Gender: ").append(genderComboBox.getSelectedItem()).append("\n");
                orderData.append("Birth Date: ").append(birthDateField.getText()).append("\n");
                orderData.append("Phone Number: ").append(phoneField.getText()).append("\n");
                orderData.append("Email: ").append(emailField.getText()).append("\n");
                orderData.append("Address: ").append(addressField.getText()).append("\n");
                orderData.append("Postal Code: ").append(postalCodeField.getText()).append("\n");
                orderData.append("City: ").append(cityField.getText()).append("\n");

                // Display the collected data in a dialog box
                JOptionPane.showMessageDialog(signInFrame, orderData.toString(), "Order is on its way!", JOptionPane.INFORMATION_MESSAGE);
                signInFrame.dispose();  // Close the sign-in frame after submitting
            });
            signInFrame.add(submitButton);

            signInFrame.setVisible(true);
        });
        summaryFrame.add(signInButton);

        // Log In Button
        JButton logInButton = new JButton("Log in");
        logInButton.addActionListener(e -> {
            // Open new window for Log-In
            JFrame logInFrame = new JFrame("Log In");
            logInFrame.setSize(800, 200);
            logInFrame.setLayout(new GridLayout(0, 2));  // Dynamic layout

            logInFrame.add(new JLabel("Email address:"));
            JTextField emailField = new JTextField();
            logInFrame.add(emailField);

            logInFrame.add(new JLabel("Password:"));
            JPasswordField passwordField = new JPasswordField();
            logInFrame.add(passwordField);

            // Submit Button for login
            JButton submitButton = new JButton("Submit Order");
            submitButton.addActionListener(submitEvent -> {
                // Collect and process the login data, then submit the order
                StringBuilder orderData = new StringBuilder("Order Details:\n");
                orderData.append(summaryArea.getText()).append("\n");
                orderData.append("Login Details:\n");
                orderData.append("Email: ").append(emailField.getText()).append("\n");

                // Display the collected data in a dialog box
                JOptionPane.showMessageDialog(logInFrame, orderData.toString(), "Order is on its way!", JOptionPane.INFORMATION_MESSAGE);
                logInFrame.dispose();  // Close the login frame after submitting
            });
            logInFrame.add(submitButton);

            logInFrame.setVisible(true);
        });
        summaryFrame.add(logInButton);

        summaryFrame.setVisible(true);
    }


    public static double calculatePizzaPrice(String pizzaName) {
        double ingredientCost = 0.0;

        // Check if the pizza name exists in the pizzaIngredients map
        if (pizzaIngredients.containsKey(pizzaName)) {
            // System.out.println("Cost breakdown for your " + pizzaName + " pizza:");

            // Get the ingredients for the pizza
            String[] ingredients = pizzaIngredients.get(pizzaName);

            // Loop through ingredients and add their prices
            for (String ingredient : ingredients) {
                if (ingredientPrices.containsKey(ingredient)) {
                    double price = ingredientPrices.get(ingredient);
                    ingredientCost += price;
                    // System.out.println(ingredient + ": $" + price);
                } else {
                    System.out.println(ingredient + ": Not available in the price list.");
                }
            }

            // Add 40% profit margin
            double profitMargin = 0.40 * ingredientCost;
            double priceWithProfit = ingredientCost + profitMargin;

            // Add 9% VAT
            double vat = 0.09 * priceWithProfit;
            double finalPrice = priceWithProfit + vat;


            // System.out.println("Ingredient Cost: $" + ingredientCost);
            // System.out.println("Profit (40%): $" + profitMargin);
            // System.out.println("VAT (9%): $" + vat);
            // System.out.println("Total Price: $" + finalPrice);

            return finalPrice;

        } else {
            System.out.println("Pizza not found.");
            return 0.0;
        }
    }


    public static void main(String[] args) {
        // Start the GUI
        SwingUtilities.invokeLater(() -> {
            PizzaOrderingApp app = new PizzaOrderingApp();
            app.setVisible(true);

        });
    }
}


