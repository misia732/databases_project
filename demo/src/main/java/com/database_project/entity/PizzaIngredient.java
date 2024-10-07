package com.database_project.entity;

public class PizzaIngredient {
    @Override
    public String toString() {
        return "PizzaIngredient [pizzaID=" + pizzaID + ", ingredientID=" + ingredientID + "]";
    }

    private int pizzaID;
    private int ingredientID;
    
    public PizzaIngredient(int pizzaID, int ingredientID) {
        this.pizzaID = pizzaID;
        this.ingredientID = ingredientID;
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
    
}
