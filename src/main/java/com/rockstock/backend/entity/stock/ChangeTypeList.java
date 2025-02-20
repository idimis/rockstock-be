package com.rockstock.backend.entity.stock;

public enum ChangeTypeList {
    // Inbound (Stock Increase)
    PURCHASE_RECEIVED,
    CUSTOMER_RETURN,
    STOCK_ADJUSTMENT_POSITIVE,

    // Outbound (Stock Decrease)
    SALES_DISPATCHED,
    RETURN_TO_SUPPLIER,
    STOCK_ADJUSTMENT_NEGATIVE,
    DAMAGED_OR_EXPIRED,

    // Internal Movement
    TRANSFER // Warehouse to warehouse movement
}
