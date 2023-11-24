package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class records extends JFrame {
    private JTextField ID, Name, Age;

    //ques 07
    public records() {
        setTitle("Student Record Entry");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and place components
        JPanel panel = new JPanel(null);

        JLabel labelID = new JLabel("Student ID:");
        labelID.setBounds(10, 20, 80, 25);
        panel.add(labelID);

        ID = new JTextField();
        ID.setBounds(100, 20, 150, 25);
        panel.add(ID);

        JLabel labelName = new JLabel("Name:");
        labelName.setBounds(10, 50, 80, 25);
        panel.add(labelName);

        Name = new JTextField();
        Name.setBounds(100, 50, 150, 25);
        panel.add(Name);

        JLabel labelAge = new JLabel("Age:");
        labelAge.setBounds(10, 80, 80, 25);
        panel.add(labelAge);

        Age = new JTextField();
        Age.setBounds(100, 80, 150, 25);
        panel.add(Age);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 110, 80, 25);
        panel.add(submitButton);
        JButton update = new JButton("Update");
        update.setBounds(100, 110, 80, 25);
        panel.add(update);
        JButton delete = new JButton("Delete");
        delete .setBounds(200, 110, 80, 25);
        panel.add(delete );

        JButton showTableButton = new JButton("Show Table");
        showTableButton.setBounds(300, 110, 120, 25);
        panel.add(showTableButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });
        panel.add(submitButton);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);

        showTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTable();
            }
        });
        panel.add(showTableButton);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               updateAge();
            }
        });
    }
    //ques04
   private  void updateAge() {
        String employeeID = ID.getText();
        String newAgeStr = Age.getText();

        if (employeeID.isEmpty() || newAgeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty Field Detected!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int newAge = Integer.parseInt(newAgeStr);

            String url = "jdbc:mysql://localhost:3306/students";
            String username = "root";
            String password = "fast123";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "UPDATE student SET age = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, newAge);
                    preparedStatement.setString(2, employeeID);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Age Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        ID.setText("");
                        Age.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Employee ID not found or Age not updated.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "New Age is Invalid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//ques03
    private void deleteRecord() {
        String studentID = ID.getText();

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty Field Detected!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String url = "jdbc:mysql://localhost:3306/students";
            String username = "root";
            String password = "fast123";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "DELETE FROM student WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, studentID);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Record Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        ID.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Student ID not found or Record not deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //ques02
    private void insertRecord() {
        String studentID = ID.getText();
        String name = Name.getText();
        String ageStr = Age.getText();

        if (studentID.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);

            String url = "jdbc:mysql://localhost:3306/students";
            String username = "root";
            String password = "fast123";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO student (id, name, age) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, studentID);
                    preparedStatement.setString(2, name);
                    preparedStatement.setInt(3, age);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Record inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                       ID.setText("");
                        Name.setText("");
                        Age.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to insert record", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTable() {
        String url = "jdbc:mysql://localhost:3306/students";
        String username = "root";
        String password = "fast123";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                JFrame tableFrame = new JFrame("Student Records");
                tableFrame.setSize(400, 300);
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableFrame.setLocationRelativeTo(null);

                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                tableFrame.add(scrollPane);

                while (resultSet.next()) {
                    int studentID = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");

                    // Append the record to the text area
                    textArea.append("Student ID: " + studentID + ", Name: " + name + ", Age: " + age + "\n");
                }

                tableFrame.setVisible(true);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error retrieving records: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new records();
            }
        });
    }
}