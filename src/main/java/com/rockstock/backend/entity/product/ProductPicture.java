package com.purwadhika.rockstock.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Table(name = "product_pictures", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_picture_id_gen")
    @SequenceGenerator(name = "product_picture_id_gen", sequenceName = "product_picture_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "product_picture_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "product_picture_url", nullable = false)
    private String productPictureUrl;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
