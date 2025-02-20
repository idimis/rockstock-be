package com.rockstock.backend.entity.payment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payment_categories", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_category_id_gen")
    @SequenceGenerator(name = "payment_category_id_gen", sequenceName = "payment_category_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "payment_category_id", nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
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
//    @JsonManagedReference
//    @OneToMany(mappedBy = "paymentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PaymentMethod> paymentMethods = new HashSet<>();
}
