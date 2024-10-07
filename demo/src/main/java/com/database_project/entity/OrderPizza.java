package com.database_project.entity;

public class OrderPizza {
    private int orderID;
    private int pizzaID;
    private int quantity;
    public OrderPizza(int orderID, int pizzaID, int quantity) {
        this.orderID = orderID;
        this.pizzaID = pizzaID;
        this.quantity = quantity;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getPizzaID() {
        return pizzaID;
    }
    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "OrderPizza [orderID=" + orderID + ", pizzaID=" + pizzaID + ", quantity=" + quantity + "]";
    }
    
}
