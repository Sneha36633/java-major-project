package com.stockwise.gui;

import com.stockwise.db.DatabaseManager;
import com.stockwise.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public InventoryPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5));
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addBtn = new JButton("Add Product");

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Quantity:"));
        form.add(qtyField);
        form.add(new JLabel("Price:"));
        form.add(priceField);
        form.add(addBtn);

        add(form, BorderLayout.SOUTH);
        loadProducts();

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                double price = Double.parseDouble(priceField.getText());

                DatabaseManager.executeUpdate(
                        "INSERT INTO products (name, quantity, price) VALUES ('" + name + "', " + qty + ", " + price + ")");
                loadProducts();
                nameField.setText("");
                qtyField.setText("");
                priceField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
            }
        });
    }

    private void loadProducts() {
        try {
            model.setRowCount(0);
            ResultSet rs = DatabaseManager.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
