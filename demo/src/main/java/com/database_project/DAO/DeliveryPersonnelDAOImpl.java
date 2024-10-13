package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.DeliveryPersonnel;

public class DeliveryPersonnelDAOImpl implements DeliveryPersonnelDAO {

    private Connection conn;

    public DeliveryPersonnelDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DeliveryPersonnel deliveryPersonnel) {
        if (!deliveryPersonnelExistsByName(deliveryPersonnel.getFirstName(), deliveryPersonnel.getLastName())) {
            String query = "INSERT INTO deliveryPersonnel (firstName, lastName, postalCode, status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, deliveryPersonnel.getFirstName());
                pstmt.setString(2, deliveryPersonnel.getLastName());
                pstmt.setString(3, deliveryPersonnel.getPostalcode());
                pstmt.setString(4, deliveryPersonnel.getStatus());


                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            deliveryPersonnel.setID(id);
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
    public void delete(DeliveryPersonnel deliveryPersonnel) {
        if (deliveryPersonnelExistsByID(deliveryPersonnel.getID())) {
            String query = "DELETE FROM deliveryPersonnel WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, deliveryPersonnel.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Delivery personnel does not exist.");
        }
    }

    @Override
    public void update(DeliveryPersonnel deliveryPersonnel) {
        String query = "UPDATE deliveryPersonnel SET firstName = ?, lastName = ?, postalCode = ?, status = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, deliveryPersonnel.getFirstName());
            pstmt.setString(2, deliveryPersonnel.getLastName());
            pstmt.setString(3, deliveryPersonnel.getPostalcode());
            pstmt.setString(4, deliveryPersonnel.getStatus());
            pstmt.setInt(5, deliveryPersonnel.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public DeliveryPersonnel findByID(int id) {
        String query = "SELECT * FROM deliveryPersonnel WHERE ID = ?";
        DeliveryPersonnel deliveryPersonnel = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("ID");
                    deliveryPersonnel = new DeliveryPersonnel(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("postalCode"),
                        rs.getString("status")
                    );
                    deliveryPersonnel.setID(id);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return deliveryPersonnel;
    }

    @Override
    public DeliveryPersonnel findAvailablePersonnel() {
        String query = "SELECT * FROM deliveryPersonnel WHERE status = ? LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "Available");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                DeliveryPersonnel personnel = new DeliveryPersonnel(
                    rs.getString("firtsName"),
                    rs.getString("lastName"),
                    rs.getString("postalcode"),
                    rs.getString("status"
                ));

                return personnel;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private boolean deliveryPersonnelExistsByID(Integer id) {
        String query = "SELECT COUNT(*) FROM deliveryPersonnel WHERE ID = ?";
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

    private boolean deliveryPersonnelExistsByName(String firstName, String lastName) {
        String query = "SELECT COUNT(*) FROM deliveryPersonnel WHERE firstName = ? AND lastName = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

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
