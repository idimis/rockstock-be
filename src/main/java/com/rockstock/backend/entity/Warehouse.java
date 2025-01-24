package com.rockstock.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "warehouses", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_id_gen")
    @SequenceGenerator(name = "warehouse_id_gen", sequenceName = "warehouse_id_seq", schema = "rockstock", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    private Set<WarehouseAdmin> warehouseAdmins = new HashSet<>();
}
