package com.stockwise.gui;

import com.stockwise.db.DatabaseManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SupplierPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public SupplierPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Name", "Contact"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 3, 5, 5));
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JButton addBtn = new JButton("Add Supplier");

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Contact:"));
        form.add(contactField);
        form.add(addBtn);

        add(form, BorderLayout.SOUTH);
        loadSuppliers();

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String contact = contactField.getText();

                DatabaseManager.executeUpdate(
                        "INSERT INTO suppliers (name, contact) VALUES ('" + name + "', '" + contact + "')");
                loadSuppliers();
                nameField.setText("");
                contactField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding supplier: " + ex.getMessage());
            }
        });
    }

    private void loadSuppliers() {
        try {
            model.setRowCount(0);
            ResultSet rs = DatabaseManager.executeQuery("SELECT * FROM suppliers");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
