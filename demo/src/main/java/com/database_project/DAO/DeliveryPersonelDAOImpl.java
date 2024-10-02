package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.DeliveryPersonel;

public class DeliveryPersonelDAOImpl implements DeliveryPersonelDAO {

    private Connection conn;

    public DeliveryPersonelDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DeliveryPersonel deliveryPersonel) {
        if (!deliveryPersonelExistsByID(deliveryPersonel.getID())) {
            String query = "INSERT INTO delivery_personel (firstName, lastName, postalCode) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, deliveryPersonel.getFirstName());
                pstmt.setString(2, deliveryPersonel.getLastName());
                pstmt.setString(3, deliveryPersonel.getPostalcode());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            deliveryPersonel.setID(id);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Delivery personnel already exists.");
        }
    }

    @Override
    public void delete(DeliveryPersonel deliveryPersonel) {
        if (deliveryPersonelExistsByID(deliveryPersonel.getID())) {
            String query = "DELETE FROM delivery_personel WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deliveryPersonel.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Delivery personnel does not exist.");
        }
    }

    @Override
    public void update(DeliveryPersonel deliveryPersonel) {
        String query = "UPDATE delivery_personel SET firstName = ?, lastName = ?, postalCode = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, deliveryPersonel.getFirstName());
            pstmt.setString(2, deliveryPersonel.getLastName());
            pstmt.setString(3, deliveryPersonel.getPostalcode());
            pstmt.setInt(4, deliveryPersonel.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public DeliveryPersonel findByID(int id) {
        String query = "SELECT * FROM delivery_personel WHERE ID = ?";
        DeliveryPersonel deliveryPersonel = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    deliveryPersonel = new DeliveryPersonel(
                        rs.getInt("ID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("postalCode")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return deliveryPersonel;
    }

    private boolean deliveryPersonelExistsByID(int id) {
        String query = "SELECT COUNT(*) FROM delivery_personel WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
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
