package com.rockstock.backend.service.warehouse;

import com.rockstock.backend.entity.warehouse.Warehouse;

public interface FindNearestWarehouseService {
    Warehouse findNearestWarehouse(String latitude, String longitude);
}
