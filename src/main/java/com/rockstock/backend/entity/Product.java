package com.rockstock.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_id_seq", schema = "rockstock", allocationSize = 1)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonIgnore
    private ProductCategory productCategory;

    @NotBlank(message = "Product name is mandatory")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotBlank(message = "Detail is mandatory")
    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "slug", nullable = false)
    private String slug;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Relationships
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<ProductPicture> productPictures = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void generateSlug() {
        if (this.productName != null && (this.slug == null || this.slug.isEmpty())) {
            this.slug = toSlug(this.productName);
        }
    }

    private String toSlug(String input) {
        String nonWhitespace = input.trim().replaceAll("\\s+", "-");
        String slug = nonWhitespace.toLowerCase();
        return slug;
    }
}
