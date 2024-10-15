package com.database_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.database_project.DAO.CustomerDAO;
import com.database_project.DAO.CustomerDAOImpl;
import com.database_project.DAO.DiscountCodeDAO;
import com.database_project.DAO.DiscountCodeDAOImpl;
import com.database_project.DAO.DrinkAndDesertDAO;
import com.database_project.DAO.DrinkAndDesertDAOImpl;
import com.database_project.DAO.IngredientDAO;
import com.database_project.DAO.IngredientDAOImpl;
import com.database_project.DAO.OrderDAO;
import com.database_project.DAO.OrderDAOImpl;
import com.database_project.DAO.OrderDrinkAndDesertDAO;
import com.database_project.DAO.OrderDrinkAndDesertDAOImpl;
import com.database_project.DAO.OrderPizzaDAO;
import com.database_project.DAO.OrderPizzaDAOImpl;
import com.database_project.DAO.PizzaDAO;
import com.database_project.DAO.PizzaDAOImpl;
import com.database_project.entity.Customer;
import com.database_project.entity.DiscountCode;
import com.database_project.entity.DrinkAndDesert;
import com.database_project.entity.Ingredient;
import com.database_project.entity.Order;
import com.database_project.entity.OrderDrinkAndDesert;
import com.database_project.entity.OrderPizza;
import com.database_project.entity.Pizza;

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

    StringBuilder discountMessage = new StringBuilder("Discounts Applied:\n");

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
        System.out.println("Order initialized with orderID=" + id);
        return id;
    }

    public double placeOrder(int orderID, List<OrderPizza> pizzas, List<OrderDrinkAndDesert> drinksAndDesserts, String discountCodeID) throws SQLException {
        Order order = orderDAO.findByID(orderID);
        int customerID = order.getCustomerID();
        Customer customer = customerDAO.findByID(customerID);
        if (pizzas == null || pizzas.isEmpty()) {
            System.out.println("Ordered not placed. An order must include at least one pizza.");
            return -1;
        }

        double price = pizzasPrice(pizzas) + drinkAndDesertPrice(drinksAndDesserts);
        
        System.out.println("Full price: " + price);

        // free pizza and drink on birthday
        if(isBirthday(customerID)){
            price -= theLowestPizzaPrice(pizzas);
            price -= theLowestDrinkAndDesertPrice(drinksAndDesserts);
            System.out.println("Birthday discount applied");
            System.out.println("New price: " + price);
            discountMessage.append("- Free pizza and drink for birthday\n");
            
        }


        // if customer included discount code
        if(!discountCodeID.isEmpty()){
            DiscountCode discountCode = discountCodeDAO.findByID(discountCodeID);
            if(discountCode == null){
                System.out.println("Discount code " + discountCodeID +  " does not exist.");
            }
            else if (discountCode.isUsed()){
                System.out.println("Discount code " + discountCodeID +  " is used.");
            }
            else{
                double percentage =  discountCode.getPercentage() / 100;
                price *= (1 - percentage);
                System.out.println("Discount code applied.");
                discountMessage.append(String.format("- %.2f%% discount from code: %s\n", percentage * 100, discountCodeID));
                System.out.println("New price: " + price);
            }
        }

        // 10% dicount if 10 pizzas ordered
        int pizzaCount = customer.getPizzaCount();
        System.out.println("Current pizza count: " + pizzaCount);
        if(pizzaCount >= 10){
            price *= 0.9;
            System.out.print("-10% dicount applied.");
            discountMessage.append("- 10% discount for ordering 10 pizzas\n");
            System.out.println("New price: " + price);
            customer.setPizzaCount(pizzaCount-10);
            System.out.println("Current pizza count: " + customer.getPizzaCount());
        }

        // number of ordered pizzas
        int n = pizzasNumber(pizzas);
        int currentPizzaCount = customer.getPizzaCount();
        int newPizzaCount = currentPizzaCount + n;
        System.out.println("Old pizza count : " + currentPizzaCount);
        System.out.println("Number of pizzas ordered: " + n);
        System.out.println("New pizza count: " + newPizzaCount);
        customer.setPizzaCount(newPizzaCount);
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
            double pizzaPrice = calculatePizzaPrice(pizzaDAO.findByID(pizzaID)) * quantity;
            price += pizzaPrice;
        }
        System.out.println("Pizzas price: " + price);
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
        int id = pizza.getID();
        List<Ingredient> ingredients = pizzaDAO.listIngredients(id);
        for(Ingredient ingredient : ingredients){
            double iprice = ingredient.getPrice();
            price += iprice;
        }
        System.out.println("Ingredients price: " + price);
        price *= 1.4;
        price *= 1.09;
        price = Math.round(price * 100.0) / 100.0;
        System.out.println("Price with vat and profit: " + price);
        return price;
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
            System.out.println("Order for cancelation not found.");
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
        
        return discountMessage.toString();
    }

}
