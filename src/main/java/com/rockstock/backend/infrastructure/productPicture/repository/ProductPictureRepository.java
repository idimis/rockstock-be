package com.rockstock.backend.infrastructure.productPicture.repository;

import com.rockstock.backend.entity.product.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    List<ProductPicture> findByProductIdOrderByPositionAsc(Long productId);

    int countByProductProductId(Long productId);

    @Modifying
    @Query("UPDATE ProductPicture pp SET pp.isMain = false WHERE pp.product.productId = :productId")
    void resetMainPicture(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE ProductPicture p SET p.position = p.position - 1 WHERE p.product.id = :productId AND p.position > :deletedPosition")
    void updatePositionsAfterDeletion(@Param("productId") Long productId, @Param("deletedPosition") int deletedPosition);
}