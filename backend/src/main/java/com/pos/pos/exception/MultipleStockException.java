package com.pos.pos.exception;

import java.util.List;

public class MultipleStockException extends RuntimeException {
    private final List<StockError> stockErrors;

    public MultipleStockException(List<StockError> stockErrors) {
        super("Multiple products have insufficient stock");
        this.stockErrors = stockErrors;
    }

    public List<StockError> getStockErrors() {
        return stockErrors;
    }
}
