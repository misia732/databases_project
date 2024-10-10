package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database_project.entity.DiscountCode;

public class DiscountCodeDAOImpl implements DiscountCodeDAO {

    private Connection conn;

    public DiscountCodeDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(DiscountCode discountCode) {
        if (!discountCodeExistsByID(discountCode.getID())) {
            String query = "INSERT INTO discountCode (ID, isUsed, percentage) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, discountCode.getID());
                pstmt.setBoolean(2, discountCode.isUsed());
                pstmt.setDouble(3, discountCode.getPercentage());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Discount code already exists.");
        }
    }

    @Override
    public void delete(DiscountCode discountCode) {
        if (discountCodeExistsByID(discountCode.getID())) {
            String query = "DELETE FROM discount_code WHERE ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, discountCode.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Discount code does not exist.");
        }
    }

    @Override
    public void update(DiscountCode discountCode) {
        String query = "UPDATE discount_code SET isUsed = ?, percentage = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, discountCode.isUsed());
            pstmt.setDouble(2, discountCode.getPercentage());
            pstmt.setString(3, discountCode.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public DiscountCode findByID(String id) {
        String query = "SELECT * FROM discount_code WHERE ID = ?";
        DiscountCode discountCode = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    discountCode = new DiscountCode(
                        rs.getString("ID"),
                        rs.getBoolean("isUsed"),
                        rs.getDouble("percentage")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return discountCode;
    }

    private boolean discountCodeExistsByID(String id) {
        String query = "SELECT COUNT(*) FROM discount_code WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
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
