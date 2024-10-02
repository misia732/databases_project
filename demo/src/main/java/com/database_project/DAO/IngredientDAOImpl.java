package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.Ingredient;

public class IngredientDAOImpl implements IngredientDAO {

    private Connection conn;

    public IngredientDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Ingredient ingredient) {
        if (!existsByName(ingredient.getName())) {
            String query = "INSERT INTO ingredient (name, price, isVegeterian, isVegan) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, ingredient.getName());
                pstmt.setDouble(2, ingredient.getPrice());
                pstmt.setBoolean(3, ingredient.isVegeterian());
                pstmt.setBoolean(4, ingredient.isVegan());

                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ingredient.setID(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Ingredient already exists.");
        }
    }

    @Override
    public void delete(Ingredient ingredient) {
        if (existsByName(ingredient.getName())) {
            String query = "DELETE FROM ingredient WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, ingredient.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Ingredient does not exist.");
        }
    }

    @Override
    public void update(Ingredient ingredient) {
        String query = "UPDATE ingredient SET name = ?, price = ?, isVegeterian = ?, isVegan = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, ingredient.getName());
            pstmt.setDouble(2, ingredient.getPrice());
            pstmt.setBoolean(3, ingredient.isVegeterian());
            pstmt.setBoolean(4, ingredient.isVegan());
            pstmt.setInt(5, ingredient.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Ingredient findByName(String name) {
        String query = "SELECT * FROM ingredient WHERE name = ?";
        Ingredient ingredient = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ingredient = new Ingredient(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("isVegeterian"),
                        rs.getBoolean("isVegan")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ingredient;
    }

    private boolean existsByName(String name) {
        String query = "SELECT COUNT(*) FROM ingredient WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
