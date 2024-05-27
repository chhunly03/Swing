package com.khrd.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class InvoiceManagement extends JFrame {
    private JTextField idField, firstNameField, lastNameField, sexField, birthDateField, houseNoField, streetNoField, khanField, provinceCityField, businessField, personalNumberField;

    public InvoiceManagement() {
        setTitle("Invoice Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(new JLabel("Search"));
        JTextField searchField = new JTextField(15);
        topPanel.add(searchField);
        JButton deleteButton = new JButton("Delete");
        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        topPanel.add(deleteButton);
        topPanel.add(insertButton);
        topPanel.add(updateButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JList<String> list = new JList<>(new DefaultListModel<>());
        centerPanel.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12, 2, 5, 5));

        // Add form fields for ID
        formPanel.add(new JLabel("ID"));
        idField = new JTextField(15);
        formPanel.add(idField);

        formPanel.add(new JLabel("First Name"));
        firstNameField = new JTextField(15);
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name"));
        lastNameField = new JTextField(15);
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Sex"));
        sexField = new JTextField(15);
        formPanel.add(sexField);

        formPanel.add(new JLabel("Birth Date (dd/MM/yyyy)"));
        birthDateField = new JTextField(15);
        formPanel.add(birthDateField);

        formPanel.add(new JLabel("House No"));
        houseNoField = new JTextField(15);
        formPanel.add(houseNoField);

        formPanel.add(new JLabel("Street No"));
        streetNoField = new JTextField(15);
        formPanel.add(streetNoField);

        formPanel.add(new JLabel("Khan"));
        khanField = new JTextField(15);
        formPanel.add(khanField);

        formPanel.add(new JLabel("Province / City"));
        provinceCityField = new JTextField(15);
        formPanel.add(provinceCityField);

        formPanel.add(new JLabel("Business"));
        businessField = new JTextField(15);
        formPanel.add(businessField);

        formPanel.add(new JLabel("Personal Number"));
        personalNumberField = new JTextField(15);
        formPanel.add(personalNumberField);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        JPanel logOutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logOutButton = new JButton("Log Out");
        logOutPanel.add(logOutButton);
        centerPanel.add(logOutPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // ActionListener for the insert button
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        // ActionListener for the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
// ActionListener for the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });


        pack();
        setLocationRelativeTo(null);
    }

    // Method to insert data
    private void insertData() {
        String url = "jdbc:postgresql://localhost:5432/swings";
        String user = "postgres";
        String password = "1111@2222@";
        String sql = "INSERT INTO tbCustomer (firstname, lastname, sex, birthdate, houseno, streetno, khan, province_city, business, personal_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date birthDate = new Date(sdf.parse(birthDateField.getText()).getTime());
            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, lastNameField.getText());
            pstmt.setString(3, sexField.getText());
            pstmt.setDate(4, birthDate);
            pstmt.setString(5, houseNoField.getText());
            pstmt.setString(6, streetNoField.getText());
            pstmt.setString(7, khanField.getText());
            pstmt.setString(8, provinceCityField.getText());
            pstmt.setString(9, businessField.getText());
            pstmt.setString(10, personalNumberField.getText());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data inserted successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert data");
            }
        } catch (ParseException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting data: " + ex.getMessage());
        }
    }

    // Method to update data
    // Method to update data
    private void updateData() {
        String url = "jdbc:postgresql://localhost:5432/swings";
        String user = "postgres";
        String password = "1111@2222@";
        String sql = "UPDATE tbCustomer SET firstname=?, lastname=?, sex=?, birthdate=?, houseno=?, streetno=?, khan=?, province_city=?, business=?, personal_number=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date birthDate = new Date(sdf.parse(birthDateField.getText()).getTime());
            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, lastNameField.getText());
            pstmt.setString(3, sexField.getText());
            pstmt.setDate(4, birthDate);
            pstmt.setString(5, houseNoField.getText());
            pstmt.setString(6, streetNoField.getText());
            pstmt.setString(7, khanField.getText());
            pstmt.setString(8, provinceCityField.getText());
            pstmt.setString(9, businessField.getText());
            pstmt.setString(10, personalNumberField.getText());

            // Set the ID from the ID field if provided
            if (!idField.getText().isEmpty()) {
                pstmt.setInt(11, Integer.parseInt(idField.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Please provide the ID for updating the record");
                return;
            }

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Data updated successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the given ID");
            }
        } catch (ParseException | SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + ex.getMessage());
        }
    }
    // Method to delete data by ID
    private void deleteData() {
        String url = "jdbc:postgresql://localhost:5432/swings";
        String user = "postgres";
        String password = "1111@2222@";
        String sql = "DELETE FROM tbCustomer WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            // Set the ID from the ID field if provided
            if (!idField.getText().isEmpty()) {
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Please provide the ID for deleting the record");
                return;
            }

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Data deleted successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the given ID");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting data: " + ex.getMessage());
        }
    }



    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "PostgreSQL Driver not found. Include it in your library path.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            new InvoiceManagement().setVisible(true);
        });
    }
}
