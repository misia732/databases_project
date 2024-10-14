package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import com.database_project.DAO.*;
import com.database_project.entity.*;

public class OrderService {

    private Connection conn;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private DiscountCodeDAO discountCodeDAO;
    private DeliveryService deliveryService;
    private OrderPizzaDAO orderPizzaDAO;
    private OrderDrinkAndDesertDAO orderDrinkAndDesertDAO;
    private PizzaDAO pizzaDAO;
    private DrinkAndDesertDAO drinkAndDesertDAO;
    private IngredientDAO ingredientDAO;

    public OrderService(Connection conn) {
        this.conn = conn;
        this.orderDAO = new OrderDAOImpl(conn);
        this.customerDAO = new CustomerDAOImpl(conn);
        this.discountCodeDAO = new DiscountCodeDAOImpl(conn);
        this.deliveryService = new DeliveryService(conn);
        this.orderPizzaDAO = new OrderPizzaDAOImpl(conn);
        this.orderDrinkAndDesertDAO = new OrderDrinkAndDesertDAOImpl(conn);
        this.pizzaDAO = new PizzaDAOImpl(conn);
        this.drinkAndDesertDAO = new DrinkAndDesertDAOImpl(conn);
        this.ingredientDAO = new IngredientDAOImpl(conn);
    }

    public int initializeNewOrder(int customerID){
        Customer customer = customerDAO.findByID(customerID);
        if (customer == null) {
            System.out.println("Customer not found.");
        }
        Order order = new Order(customerID, null, null, null, null);
        int id = orderDAO.insert(order);
        System.out.println("Order initialized: " + id);
        return id;
    }

    public double placeOrder(int orderID, List<OrderPizza> pizzas, List<OrderDrinkAndDesert> drinksAndDesserts, String discountCodeID) throws SQLException {
        Order order = orderDAO.findByID(orderID);
        int customerID = order.getCustomerID();
        Customer customer = customerDAO.findByID(customerID);
        if (pizzas == null || pizzas.isEmpty()) {
            System.out.println("An order must include at least one pizza.");
            return -1;
        }

        double price = pizzasPrice(pizzas) + drinkAndDesertPrice(drinksAndDesserts);
        
        // free pizza and drink on birthday
        if(isBirthday(customerID)){
            System.out.println(price);
            price -= theLowestPizzaPrice(pizzas);
            System.out.println(price);
            price -= theLowestDrinkAndDesertPrice(drinksAndDesserts);
            System.out.println(price);
            System.out.println("Birthday discount applied");
            
        }


        // if customer included discount code
        if(!discountCodeID.isEmpty()){
            DiscountCode discountCode = discountCodeDAO.findByID(discountCodeID);
            if(discountCode == null){
                System.out.println("Discount code does not exist.");
            }
            else if (discountCode.isUsed()){
                System.out.println("Discount code is used.");
            }
            else{
                price *= (1 - discountCode.getPercentage());
            }
        }

        // number of ordered pizzas
        int n = pizzasNumber(pizzas);
        System.out.println("number of pizzas ordered: " + n);
        customer.setPizzaCount(n);

        // 10% dicount if 10 pizzas ordered
        if(customer.getPizzaCount()%10 == 0){
            price *= 0.9;
            
            System.out.println(price);
        }

        customerDAO.update(customer);

        LocalDateTime now = LocalDateTime.now();
        order.setPlacementTime(now);
        order.setStatus("being prepared");
        order.setPrice(price);
        orderDAO.update(order);

        return price;
    }

    public int pizzasNumber(List<OrderPizza> orderPizzas) throws SQLException {
        int n = 0;
        for (OrderPizza orderPizza : orderPizzas) {
            int quantity = orderPizza.getQuantity();
            n += quantity;
        }
        return n;
    }

    public double pizzasPrice(List<OrderPizza> orderPizzas) throws SQLException {
        double price = 0;
        for (OrderPizza orderPizza : orderPizzas) {
            int pizzaID = orderPizza.getPizzaID();
            int quantity = orderPizza.getQuantity();
            double pizzaPrice = calculatePizzaPrice(pizzaDAO.findByID(pizzaID));
            price =+ pizzaPrice;
        }
        return price;
    }

    private double theLowestPizzaPrice(List<OrderPizza> orderPizzas){
        List<Pizza> pizzas = new ArrayList<>();
        for(OrderPizza orderPizza : orderPizzas){
            pizzas.add(pizzaDAO.findByID(orderPizza.getPizzaID()));
        }
        double min = calculatePizzaPrice(pizzas.get(0));
        pizzas.remove(0);
        for(Pizza pizza : pizzas){
            double price = calculatePizzaPrice(pizza);
            if(price < min) min = price;
        }
        System.out.println("min pizza price: " + min);
        return min;
    }

    private double calculatePizzaPrice(Pizza pizza){
        double price = 0;
        int id = pizza.getID();
        List<Ingredient> ingredients = pizzaDAO.listIngredients(id);
        for(Ingredient ingredient : ingredients){
            double iprice = ingredient.getPrice();
            price += iprice;
        }
        price *= 1.4;
        price *= 1.09;
        return Math.round(price * 100.0) / 100.0;
    }

    public double drinkAndDesertPrice(List<OrderDrinkAndDesert> orderDrinksAndDeserts) throws SQLException {
        double price = 0;
        for (OrderDrinkAndDesert orderDrinkAndDesert : orderDrinksAndDeserts) {
            int id = orderDrinkAndDesert.getDrinkAndDesertID();
            DrinkAndDesert drinkAndDesert = drinkAndDesertDAO.findByID(id);
            price += calculateDrinkAndDesertPrice(drinkAndDesert) * orderDrinkAndDesert.getQuantity();
            System.out.println("Ordered drink or dessert price: " + price);
        }
        return price;
    }

    private double theLowestDrinkAndDesertPrice(List<OrderDrinkAndDesert> orderDrinksAndDeserts){
        List<DrinkAndDesert> drinksAndDeserts = new ArrayList<>();
        for(OrderDrinkAndDesert orderDrinkAndDesert : orderDrinksAndDeserts){
            drinksAndDeserts.add(drinkAndDesertDAO.findByID(orderDrinkAndDesert.getDrinkAndDesertID()));
        }
        double min = calculateDrinkAndDesertPrice(drinksAndDeserts.get(0));
        drinksAndDeserts.remove(0);
        for(DrinkAndDesert drinkAndDesert : drinksAndDeserts){
            double price = calculateDrinkAndDesertPrice(drinkAndDesert);
            if(price < min) min = price;
        }
        return min;
    }

    private double calculateDrinkAndDesertPrice(DrinkAndDesert drinkAndDesert){
        System.out.println(drinkAndDesert.getPrice());
        return drinkAndDesert.getPrice();
    }

    private boolean isBirthday(int customerID){
        Customer customer = customerDAO.findByID(customerID);
        LocalDate today = LocalDate.now();
        LocalDate birthdate = customer.getBirthDate().toLocalDate();
        if (birthdate.getDayOfMonth() == today.getDayOfMonth() && birthdate.getMonth() == today.getMonth()) {
            return true;
        }
        return false;
    }

    public List<Pizza> getToDoPizzas() throws SQLException {
        List<Order> beingPrepared = orderDAO.findOrdersByStatus("being prepared");
        List<Pizza> todo = new ArrayList<>();
        
        for (Order order : beingPrepared) {
            List<OrderPizza> orderPizzas = orderPizzaDAO.findByOrderID(order.getID());
            for (OrderPizza orderPizza : orderPizzas) {
                int pizzaID = orderPizza.getPizzaID();
                int quantity = orderPizza.getQuantity();
                for(int i = 0; i<quantity; i++){
                    todo.add(pizzaDAO.findByID(pizzaID));
                }
            }
        }
        System.out.println(todo);
        return todo;
    }
    
    public boolean cancelOrder(int orderId) throws SQLException {
        Order order = orderDAO.findByID(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return false;
        }
        
        LocalDateTime orderTime = order.getPlacementTime();
        LocalDateTime now = LocalDateTime.now();
        
        // check if 5 minutes have passed since the placenet time
        if (Duration.between(orderTime, now).toMinutes() < 5) {
            orderDAO.delete(order);
            System.out.println("Order canceled.");
            return true;
        } else {
            System.out.println("Order cannot be canceled after 5 minutes.");
            return false;
        }
    }

    // Setter for deliveryPersonnelId
    public void setDeliveryPersonnelId(int deliveryPersonnelId) {
        this.deliveryService = deliveryService;
    }

    public void changeStatus(int orderID, String newStatus){
        Order order = orderDAO.findByID(orderID);
        order.setStatus(newStatus);
        orderDAO.update(order);
    }

    public String getAppliedDiscounts(int customerID, List<OrderPizza> pizzas, List<OrderDrinkAndDesert> drinksAndDesserts, String discountCodeID) throws SQLException {
        StringBuilder discountMessage = new StringBuilder("Discounts Applied:\n");
        boolean hasDiscount = false;

        // Free pizza and drink on birthday
        if (isBirthday(customerID)) {
            hasDiscount = true;
            discountMessage.append("- Free pizza and drink for birthday\n");
        }

        // 10% discount if 10 pizzas ordered
        if (customerDAO.findByID(customerID).getPizzaCount() % 10 == 0) {
            hasDiscount = true;
            discountMessage.append("- 10% discount for ordering 10 pizzas\n");
        }

        // Check for discount code
        // if (!discountCodeID.isEmpty()) {
        //     DiscountCode discountCode = discountCodeDAO.findByID(discountCodeID);
        //     if (discountCode != null && !discountCode.isUsed()) {
        //         hasDiscount = true;
        //         discountMessage.append(String.format("- %.2f%% discount from code: %s\n", discountCode.getPercentage() * 100, discountCodeID));
        //     } else if (discountCode == null) {
        //         discountMessage.append("- Invalid discount code\n");
        //     } else {
        //         discountMessage.append("- Discount code has already been used\n");
        //     }
        // }

        if (!hasDiscount) {
            return "No discounts applied.";
        }

        return discountMessage.toString();
    }

}
