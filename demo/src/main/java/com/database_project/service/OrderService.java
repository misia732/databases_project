package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

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

    public OrderService(Connection conn) {
        this.conn = conn;
        this.orderDAO = new OrderDAOImpl(conn);
        this.customerDAO = new CustomerDAOImpl(conn);
        this.discountCodeDAO = new DiscountCodeDAOImpl(conn);
        this.deliveryService = new DeliveryService(conn);
        this.orderPizzaDAO = new OrderPizzaDAOImpl(conn);
        this.orderDrinkAndDesertDAO = new OrderDrinkAndDesertDAOImpl(conn);
        this.pizzaDAO = new PizzaDAOImpl(conn);
    }

    public void placeOrder(int customerID, List<OrderPizza> pizzas, List<OrderDrinkAndDesert> drinksAndDesserts) throws SQLException {
        Customer customer = customerDAO.findByID(customerID);
        if (customer == null) {
            System.out.println("Customer not found.");
        }
        if (pizzas == null || pizzas.isEmpty()) {
            System.out.println("An order must include at least one pizza.");
            return;
        }

        //discounts?

        Order order = new Order(customerID, null, "placed", "", 0);
        orderDAO.insert(order);

        for (OrderPizza pizza : pizzas) {
            pizza.setOrderID(order.getID());
            orderPizzaDAO.insert(pizza);
        }
        for (OrderDrinkAndDesert drinkAndDessert : drinksAndDesserts) {
            drinkAndDessert.setOrderID(order.getID());
            orderDrinkAndDesertDAO.insert(drinkAndDessert);
        }

        deliveryService.assignDeliveryPersonnel(order);
        deliveryService.updateDeliveryTime(order.getID());
    }

    public double pizzasPrice(Order order) throws SQLException {
        int orderID = order.getID();
        List<OrderPizza> orderPizzas = orderPizzaDAO.findByOrderID(orderID);
        List<Pizza> pizzas = orderPizzaDAO.listPizzas(orderID);
        double price = 0;
        orderPizzaDAO.findByOrderID(orderID);
        for (OrderPizza orderPizza : orderPizzas) {
            int pizzaID = orderPizza.getPizzaID();
            int quantity = orderPizza.getQuantity();
            List<Ingredient> ingredients = pizzaDAO.listIngredients(pizzaID);
            for(Ingredient ingredient : ingredients){
                price += ingredient.getPrice() * quantity;
            }
        }
        if(isBirthday(order.getCustomerID())){
            price -= theLowestPizzaPrice(pizzas);
        } 
        // discounts
        return price;
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

    private double theLowestPizzaPrice(List<Pizza> pizzas){
        double min = calculatePizzaPrice(pizzas.get(0));
        pizzas.remove(0);
        for(Pizza pizza : pizzas){
            double price = calculatePizzaPrice(pizza);
            if(price < min) min = price;
        }
        return min;
    }

    private double calculatePizzaPrice(Pizza pizza){
        double price = 0;
        List<Ingredient> ingredients = pizzaDAO.listIngredients(pizza.getID());
        for(Ingredient ingredient : ingredients){
            price += ingredient.getPrice();
        }
        return price;
    }

    public List<OrderDrinkAndDesert> getDrinksAndDessertsByOrderID(int orderID) throws SQLException {
        return orderDrinkAndDesertDAO.findByOrderID(orderID);
    }
    
    private String generateRandomCode() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 8) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            code.append(CHARS.charAt(index));
        }
        return code.toString();
    }
}
