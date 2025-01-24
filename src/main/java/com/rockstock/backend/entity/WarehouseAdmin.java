package com.rockstock.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_admins", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_admin_id_gen")
    @SequenceGenerator(name = "warehouse_admin_id_gen", sequenceName = "warehouse_admin_id_seq", schema = "rockstock", allocationSize = 1)
    private Long id;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}
