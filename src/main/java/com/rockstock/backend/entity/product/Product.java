package com.rockstock.backend.entity.product;

//import com.rockstock.backend.entity.cart.CartItem;
//import com.rockstock.backend.entity.order.OrderItem;
import com.rockstock.backend.entity.stock.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "detail", nullable = false)
    private String detail;

    @NotNull
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @NotNull
    @Column(name = "totalStocks", nullable = false, precision = 5)
    private BigDecimal totalStocks;

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
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductPicture> productPictures = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stock> stocks = new HashSet<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<CartItem> cartItems = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<OrderItem> orderItems = new HashSet<>();
}
