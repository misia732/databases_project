package com.database_project.entity;

public class PizzaIngredient {
    @Override
    public String toString() {
        return "PizzaIngredient [pizzaID=" + pizzaID + ", ingredientID=" + ingredientID + ", quantity=" + quantity
                + "]";
    }

    private int pizzaID;
    private int ingredientID;
    private int quantity;
    
    public PizzaIngredient(int pizzaID, int ingredientID, int quantity) {
        this.pizzaID = pizzaID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
    }

    public int getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
