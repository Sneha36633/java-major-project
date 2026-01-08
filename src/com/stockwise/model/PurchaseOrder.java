package com.stockwise.model;

public class PurchaseOrder {
    private int id;
    private int productId;
    private int supplierId;
    private int quantity;
    private String date;

    public PurchaseOrder(int id, int productId, int supplierId, int quantity, String date) {
        this.id = id;
        this.productId = productId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.date = date;
    }

    public PurchaseOrder(int productId, int supplierId, int quantity, String date) {
        this.productId = productId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
