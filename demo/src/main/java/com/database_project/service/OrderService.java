package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
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
    }

    public void placeOrder(int customerID, List<OrderPizza> pizzas, List<OrderDrinkAndDesert> drinksAndDesserts, String discountCodeID) throws SQLException {
        Customer customer = customerDAO.findByID(customerID);
        if (customer == null) {
            System.out.println("Customer not found.");
        }
        if (pizzas == null || pizzas.isEmpty()) {
            System.out.println("An order must include at least one pizza.");
            return;
        }

        double price = pizzasPrice(pizzas) + drinkAndDesertPrice(drinksAndDesserts);

        // free pizza and drink on birthday
        if(isBirthday(customerID)){
            price -= theLowestPizzaPrice(pizzas);
            price -= theLowestDrinkAndDesertPrice(drinksAndDesserts);
        }

        // 10% dicount if 10 pizzas ordered
        if(customer.getPizzaCount()%10 == 0){
            price *= 0.9;
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

        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(customerID, now, "being prepared", null, null);
        orderDAO.insert(order);
    }

    public double pizzasPrice(List<OrderPizza> orderPizzas) throws SQLException {
        double price = 0;
        for (OrderPizza orderPizza : orderPizzas) {
            int pizzaID = orderPizza.getPizzaID();
            int quantity = orderPizza.getQuantity();
            double pizzaPrice = calculatePizzaPrice(pizzaDAO.findByID(pizzaID));
            price =+ calculatePizzaPrice(pizzaDAO.findByID(pizzaID));
        }
        // 40% prifit margin + 9% VAT
        price *= 1.4;
        price *= 1.09;
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

    public double drinkAndDesertPrice(List<OrderDrinkAndDesert> orderDrinksAndDeserts) throws SQLException {
        double price = 0;
        for (OrderDrinkAndDesert orderDrinkAndDesert : orderDrinksAndDeserts) {
            int id = orderDrinkAndDesert.getDrinkAndDesertID();
            DrinkAndDesert drinkAndDesert = drinkAndDesertDAO.findByID(id);
            price += calculateDrinkAndDesertPrice(drinkAndDesert) * orderDrinkAndDesert.getQuantity();
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
        return todo;
    }
}
