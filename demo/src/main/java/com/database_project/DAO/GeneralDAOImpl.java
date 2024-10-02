package com.database_project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneralDAOImpl implements GeneralDAO {

    private Connection conn;

    public GeneralDAOImpl(Connection conn) {
        this.conn = conn;
        Statement stmt;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(String table) {
        String query = "DELETE FROM " + table;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
            System.out.println("Table " + table + "deleted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
