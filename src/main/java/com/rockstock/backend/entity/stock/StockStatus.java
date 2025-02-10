package com.rockstock.backend.entity.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock_statuses", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_status_id_gen")
    @SequenceGenerator(name = "stock_status_id_gen", sequenceName = "stock_status_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "stock_status_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }

    // Relationships
    @OneToMany(mappedBy = "stockStatus", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StockJournal> stockJournals = new HashSet<>();
}
