package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//ques01
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","fast123");
        System.out.println(connection);
        //ques 06
        try {
            connection.setAutoCommit(false);

            try {
                insert(connection, 3861, "Junaid", 19);
                update(connection, 3861, " Junaid");
                delete(connection, 3861);
                connection.commit();
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Transaction rolled back. Reason: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static void insert(Connection connection, int id, String name, int age) throws SQLException {
        String insert = "INSERT INTO student (id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insert)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
          ps.executeUpdate();
            System.out.println("Data Inserted");
        }
    }

    private static void update(Connection connection, int id, String newName) throws SQLException {
        String update = "UPDATE student SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(update)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Data updated");
        }
    }

    private static void delete(Connection connection, int id) throws SQLException {
        String delete = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(delete) ){
           ps.setInt(1, id);
           ps.executeUpdate();
            System.out.println("Data deleted");
        }
    }
}

