package com.rockstock.backend.infrastructure.productPicture.repository;

import com.rockstock.backend.entity.product.ProductPicture;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    List<ProductPicture> findByProductIdOrderByPositionAsc(Long productId);

    int countByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductPicture p SET p.isMain = false WHERE p.product.id = :productId")
    void resetMainPicture(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE ProductPicture p SET p.position = p.position - 1 WHERE p.product.id = :productId AND p.position > :deletedPosition")
    void updatePositionsAfterDeletion(@Param("productId") Long productId, @Param("deletedPosition") int deletedPosition);

    @Query("SELECT pp FROM ProductPicture pp WHERE pp.id = :pictureId AND pp.product.id = :productId AND pp.deletedAt IS NULL")
    Optional<ProductPicture> findByProductIdAndPictureId(@Param("productId") Long productId, @Param("pictureId") Long pictureId);
}