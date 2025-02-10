package com.rockstock.backend.entity.stock;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "stock_change_types", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockChangeType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_change_type_id_gen")
    @SequenceGenerator(name = "stock_change_type_id_gen", sequenceName = "stock_change_type_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "stock_change_type_id", nullable = false)
    private Long id;

    @Column(name = "change_type", nullable = false)
    private String changeType;

    @Column(name = "detail", nullable = false)
    private String detail;

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
    @OneToMany(mappedBy = "stockChangeType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StockJournal> stockJournals = new HashSet<>();
}