package com.pos.pos.model;

public class ItemDTO {
    private String productId;
    private String productCode;
    private String productName;
    private String description;
    private double mrp;
    private double unitPrice;
    private int quantity;
    private int previousQuantity;
    private String unit;
    private double discount;
    private double total;

    // ---- Getters & Setters ----
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getUnit() {return unit;}
    public void setUnit(String unit) {this.unit = unit;}

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getMrp() { return mrp; }
    public void setMrp(double mrp) { this.mrp = mrp; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getPreviousQuantity() {return  previousQuantity;}
    public void setPreviousQuantity(int previousQuantity) {this.previousQuantity = previousQuantity;}

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}