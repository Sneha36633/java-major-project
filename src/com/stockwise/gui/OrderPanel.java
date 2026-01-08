package com.stockwise.gui;

import com.stockwise.db.DatabaseManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OrderPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public OrderPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Product ID", "Supplier ID", "Quantity", "Date"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 5, 5, 5));
        JTextField productIdField = new JTextField();
        JTextField supplierIdField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField dateField = new JTextField();
        JButton addBtn = new JButton("Add Order");

        form.add(new JLabel("Product ID:"));
        form.add(productIdField);
        form.add(new JLabel("Supplier ID:"));
        form.add(supplierIdField);
        form.add(new JLabel("Quantity:"));
        form.add(qtyField);
        form.add(new JLabel("Date (YYYY-MM-DD):"));
        form.add(dateField);
        form.add(addBtn);

        add(form, BorderLayout.SOUTH);
        loadOrders();

        addBtn.addActionListener(e -> {
            try {
                int pid = Integer.parseInt(productIdField.getText());
                int sid = Integer.parseInt(supplierIdField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                String date = dateField.getText();

                DatabaseManager.executeUpdate(
                        "INSERT INTO purchase_orders (product_id, supplier_id, quantity, date) " +
                                "VALUES (" + pid + ", " + sid + ", " + qty + ", '" + date + "')");
                loadOrders();
                productIdField.setText("");
                supplierIdField.setText("");
                qtyField.setText("");
                dateField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding order: " + ex.getMessage());
            }
        });
    }

    private void loadOrders() {
        try {
            model.setRowCount(0);
            ResultSet rs = DatabaseManager.executeQuery("SELECT * FROM purchase_orders");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("supplier_id"),
                        rs.getInt("quantity"),
                        rs.getString("date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
