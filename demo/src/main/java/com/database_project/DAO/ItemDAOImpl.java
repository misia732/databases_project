package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.Item;

public class ItemDAOImpl implements ItemDAO {

    private Connection conn;

    public ItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Item item) {
        if (!existsByName(item.getName())) {
            String query = "INSERT INTO item (name, price, isVegeterian, isVegan, itemType) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, item.getName());
                pstmt.setDouble(2, item.getPrice());
                pstmt.setBoolean(3, item.isVegeterian());
                pstmt.setBoolean(4, item.isVegan());
                pstmt.setString(5, item.getItemType());

                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setID(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Item already exists.");
        }
    }

    @Override
    public void delete(Item item) {
        if (existsByName(item.getName())) {
            String query = "DELETE FROM item WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, item.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Item does not exist.");
        }
    }

    @Override
    public void update(Item item) {
        String query = "UPDATE item SET name = ?, price = ?, isVegeterian = ?, isVegan = ?, itemType = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setBoolean(3, item.isVegeterian());
            pstmt.setBoolean(4, item.isVegan());
            pstmt.setString(5, item.getItemType());
            pstmt.setInt(6, item.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Item findByName(String name) {
        String query = "SELECT * FROM item WHERE name = ?";
        Item item = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new Item(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("isVegeterian"),
                        rs.getBoolean("isVegan"),
                        rs.getString("itemType")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return item;
    }

    private boolean existsByName(String name) {
        String query = "SELECT COUNT(*) FROM item WHERE name = ?";
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
