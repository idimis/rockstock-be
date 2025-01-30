package com.rockstock.backend.infrastructure.system.product.repository;

import com.rockstock.backend.entity.Product;
import com.rockstock.backend.infrastructure.usecase.product.dto.ProductStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndOrganizerId(Long productId, Long organizerId);
    Page<Product> findByLocationNot(Pageable pageable, String location);
    Page<Product> findByOrganizerId(Pageable pageable, Long organizerId);

    @Query("SELECT p FROM Product p WHERE p.dateTimeStart > :currentDateTime AND " +
            "(LOWER(p.location) = LOWER(:location) OR :location IS NULL) AND " +
            "(LOWER(p.category) = LOWER(:category) OR :category IS NULL) AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
            "ORDER BY p.dateTimeStart ASC")
    Page<Product> findUpcomingProducts(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

    @Query("SELECT p FROM Product p WHERE p.dateTimeStart > :currentDateTime AND " +
            "(LOWER(p.location) = LOWER(:location) OR :location IS NULL) AND " +
            "(LOWER(p.category) = LOWER(:category) OR :category IS NULL) AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
            "ORDER BY p.createdAt DESC")
    Page<Product> findUpcomingProductsSortedByNewest(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

    @Query("SELECT p FROM Product p LEFT JOIN Review r ON p.id = r.productId WHERE p.dateTimeStart > :currentDateTime AND " +
            "(LOWER(p.location) = LOWER(:location) OR :location IS NULL) AND " +
            "(LOWER(p.category) = LOWER(:category) OR :category IS NULL) AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR :search IS NULL) " +
            "GROUP BY p.id " +
            "ORDER BY AVG(r.rating) DESC")
    Page<Product> findUpcomingProductsSortedByHighestRating(Pageable pageable, LocalDateTime currentDateTime, String location, String category, String search);

    @Query("SELECT p FROM Product p JOIN Transaction t ON p.id = t.product.id WHERE t.customer.id = :customerId AND p.dateTimeEnd < :currentDateTime")
    Page<Product> findPastProductsByCustomer(Long customerId, LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN Transaction t ON p.id = t.product.id WHERE t.customer.id = :customerId AND p.dateTimeEnd >= :currentDateTime")
    Page<Product> findUpcomingProductsByCustomer(Long customerId, LocalDateTime currentDateTime, Pageable pageable);

    @Query("SELECT new com.rockstock.backend.infrastructure.usecase.product.dto.ProductStatisticsDTO(p.id, p.title, COUNT(t), AVG(r.rating), SUM(t.amountPaid)) " +
            "FROM Product p " +
            "LEFT JOIN Review r ON p.id = r.productId " +
            "LEFT JOIN Transaction t ON p.id = t.product.id " +
            "WHERE p.organizer.id = :organizerId " +
            "GROUP BY p.id, p.title")
    Page<ProductStatisticsDTO> findProductStatisticsByOrganizer(@Param("organizerId") Long organizerId, Pageable pageable);

    Optional<Product> findBySlug(String slug);

    @Query("SELECT p FROM Product p JOIN Transaction t ON p.id = t.product.id " +
            "WHERE p.dateTimeStart > :currentDateTime " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(t.id) DESC")
    Page<Product> findHottestUpcomingProduct(LocalDateTime currentDateTime, Pageable pageable);
}
