package com.pos.pos.exception;

public class StockError {
    private String productName;
    private int requestedQty;
    private int availableQty;

    public StockError(String productName, int requestedQty, int availableQty) {
        this.productName = productName;
        this.requestedQty = requestedQty;
        this.availableQty = availableQty;
    }

    public String getProductName() { return productName; }
    public int getRequestedQty() { return requestedQty; }
    public int getAvailableQty() { return availableQty; }
}
