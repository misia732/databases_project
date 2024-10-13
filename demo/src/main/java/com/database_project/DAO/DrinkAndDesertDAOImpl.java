package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.DrinkAndDesert;

public class DrinkAndDesertDAOImpl implements DrinkAndDesertDAO {

    private Connection conn;

    public DrinkAndDesertDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DrinkAndDesert item) {
        if (!drinkAndDesertExistsByName(item.getName())) {
            String query = "INSERT INTO drinkAndDesert (name, price) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, item.getName());
                pstmt.setDouble(2, item.getPrice());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            item.setID(id);
                        }
                    }
                }
                System.out.println("Drink/Dessert: " + item.toString() + " inserted");
            } catch (SQLException e) {
                System.out.println("Insertion Error: " + e.getMessage());
            }
        } else {
            System.out.println("Drink/Dessert already exists.");
        }
    }

    @Override
    public void delete(DrinkAndDesert item) {
        if (drinkAndDesertExistsByName(item.getName())) {
            String query = "DELETE FROM drinkAndDesert WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, item.getID());
                pstmt.executeUpdate();
                System.out.println("Drink/Dessert: " + item.toString() + " deleted");
            } catch (SQLException e) {
                System.out.println("Deletion Error: " + e.getMessage());
            }
        } else {
            System.out.println("Drink/Dessert does not exist.");
        }
    }

    @Override
    public void update(DrinkAndDesert item) {
        String query = "UPDATE drinkAndDesert SET name = ?, price = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setInt(3, item.getID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Drink/Dessert: " + item.toString() + " updated");
            } else {
                System.out.println("Update Error: No item found with ID = " + item.getID());
            }
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }

    @Override
    public DrinkAndDesert findByName(String name) {
        String query = "SELECT * FROM drinkAndDesert WHERE name = ?";
        DrinkAndDesert item = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new DrinkAndDesert(rs.getString("name"), rs.getDouble("price"));
                }
                item.setID(rs.getInt("ID"));
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return item;
    }

    private boolean drinkAndDesertExistsByID(int id) {
        String query = "SELECT COUNT(*) FROM drinkAndDesert WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Existence Check Error1: " + e.getMessage());
        }
        return false;
    }
    @Override
    public DrinkAndDesert findByID(int id) {
        if(!drinkAndDesertExistsByID(id)){
            System.out.println("Drink or Desert does not exist");
            return null;
        }
        String query = "SELECT * FROM drinkAndDesert WHERE ID = ?";
        DrinkAndDesert item = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new DrinkAndDesert(rs.getString("name"), rs.getDouble("price"));
                }
                item.setID(rs.getInt("ID"));
            }
        } catch (SQLException e) {
            System.out.println("Retrieval Error: " + e.getMessage());
        }
        return item;
    }

    private boolean drinkAndDesertExistsByName(String name) {
        String query = "SELECT COUNT(*) FROM drinkAndDesert WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Existence Check Error2: " + e.getMessage());
        }
        return false;
    }
}
