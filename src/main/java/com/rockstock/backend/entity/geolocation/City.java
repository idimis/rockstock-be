package com.rockstock.backend.entity.geolocation;

import com.rockstock.backend.entity.user.Address;
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
@Table(name = "cities", schema = "rockstock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_id_gen")
    @SequenceGenerator(name = "city_id_gen", sequenceName = "city_id_seq", schema = "rockstock", allocationSize = 1)
    @Column(name = "city_id", nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(nullable = false, length = 10)
    private String type;

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

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();
}
