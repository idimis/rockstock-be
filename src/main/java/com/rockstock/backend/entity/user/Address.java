package com.rockstock.backend.entity.user;

import com.rockstock.backend.entity.geolocation.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Table(name = "addresses", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_gen")
    @SequenceGenerator(name = "address_id_gen", sequenceName = "address_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String label;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private String longitude;

    @NotNull
    @Column(nullable = false)
    private String latitude;

    private String note;

    @Column(name = "is_main", nullable = false)
    @ColumnDefault("false")
    private Boolean isMain = false;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
