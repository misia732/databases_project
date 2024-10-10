package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database_project.entity.*;

public class PizzaDAOImpl implements PizzaDAO {

    private Connection conn;

    public PizzaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Pizza pizza) {
        if (!pizzaExistsByName(pizza.getName())) {
            String query = "INSERT INTO pizza (name) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, pizza.getName());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            pizza.setID(id);
                        }
                    }
                }
                System.out.println("Pizza: " + pizza.toString() + " inserted");
            } catch (SQLException e) {
                System.out.println("Insertion Error: " + e.getMessage());
            }
        } else {
            System.out.println("Pizza already exists.");
        }
    }

    @Override
    public void delete(Pizza pizza) {
        if (pizzaExistsByName(pizza.getName())) {
            String query = "DELETE FROM pizza WHERE ID = ?;";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, pizza.getID());
                pstmt.executeUpdate();
                System.out.println("Pizza: " + pizza.toString() + " deleted");
            } catch (SQLException e) {
                System.out.println("Deletion Error: " + e.getMessage());
            }
        } else {
            System.out.println("Pizza does not exist.");
        }
    }

    @Override
    public void update(Pizza pizza) {
        String query = "UPDATE pizza SET name = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pizza.getName());
            pstmt.setInt(2, pizza.getID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Pizza: " + pizza.toString() + " updated");
            } else {
                System.out.println("Update Error: No pizza found with ID = " + pizza.getID());
            }
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    @Override
    public Pizza findByName(String name) {
        if(!pizzaExistsByName(name)){
            System.out.println("pizza does not exist.");
            return null;
        }
        String query = "SELECT * FROM pizza WHERE name = ?";
        Pizza pizza = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    pizza = new Pizza(rs.getString("name"));
                    pizza.setID(rs.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return pizza;
    }

    private boolean pizzaExistsByName(String name) {
        String query = "SELECT COUNT(*) FROM pizza WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Existence Check Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Pizza findByID(int id) {
        if(!pizzaExistsByID(id)){
            System.out.println("pizza does not exist.");
            return null;
        }
        String query = "SELECT * FROM pizza WHERE id = ?";
        Pizza pizza = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    pizza = new Pizza(rs.getString("name"));
                    pizza.setID(rs.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return pizza;
    }

    private boolean pizzaExistsByID(int id) {
        String query = "SELECT COUNT(*) FROM pizza WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Existence Check Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Ingredient> listIngredients(int pizzaID) {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT ingredient.ID, ingredient.name, ingredient.price, ingredient.isVegeterian, ingredient.isVegan FROM ingredient"
                    + "JOIN pizzaIngredient ON ingredient.ID = pizzaIngredient.ingredientID "
                    + "WHERE pizzaIngredient.pizzaID = ?";
                    
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, pizzaID);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Ingredient ingredient = new Ingredient(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("isVegeterian"),
                        rs.getBoolean("isVegan")
                    );
                    ingredient.setID(id);
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return ingredients;
    }
}
