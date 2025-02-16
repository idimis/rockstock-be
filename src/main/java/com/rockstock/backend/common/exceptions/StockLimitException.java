package com.rockstock.backend.common.exceptions;

public class StockLimitException extends RuntimeException {
    public StockLimitException(String message) {
        super(message);
    }
}
