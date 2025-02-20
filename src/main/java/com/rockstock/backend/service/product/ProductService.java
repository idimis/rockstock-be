package com.rockstock.backend.service.product;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductCategory;
import com.rockstock.backend.infrastructure.product.dto.CreateProductRequestDTO;
import com.rockstock.backend.infrastructure.product.dto.CreateProductResponseDTO;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.product.specification.FilterProductSpecifications;
import com.rockstock.backend.infrastructure.productCategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public CreateProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {
        if (productRepository.existsByProductNameIgnoreCase(createProductRequestDTO.getProductName())) {
            throw new RuntimeException("Product with name '" + createProductRequestDTO.getProductName() + "' already exists.");
        }

        // Lookup category using categoryId
        ProductCategory productCategory = productCategoryRepository.findByCategoryIdAndDeletedAtIsNull(createProductRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + createProductRequestDTO.getCategoryId()));

        // Map DTO to entity
        Product product = createProductRequestDTO.toProduct(productCategory);
        product.setProductCategory(productCategory);
        product.setTotalStock(BigDecimal.ZERO);
        product.setCreatedAt(OffsetDateTime.now());
        product.setUpdatedAt(OffsetDateTime.now());

        // Save the product
        Product savedProduct = productRepository.save(product);

        return CreateProductResponseDTO.fromProduct(savedProduct);
    }

    public CreateProductResponseDTO updateProduct(Long id, CreateProductRequestDTO createProductRequestDTO) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Update only the changed fields
        if (createProductRequestDTO.getProductName() != null) {
            product.setProductName(createProductRequestDTO.getProductName());
        }
        if (createProductRequestDTO.getDetail() != null) {
            product.setDetail(createProductRequestDTO.getDetail());
        }
        if (createProductRequestDTO.getPrice() != null) {
            product.setPrice(createProductRequestDTO.getPrice());
        }
        if (createProductRequestDTO.getWeight() != null) {
            product.setWeight(createProductRequestDTO.getWeight());
        }

        product.setUpdatedAt(OffsetDateTime.now());

        // Save the updated product
        Product updatedProduct = productRepository.save(product);

        // Convert to response DTO
        return CreateProductResponseDTO.fromProduct(updatedProduct);
    }

    // Get Product by ID - excluding soft-deleted products
    public CreateProductResponseDTO getProductById(Long id) {
        return productRepository.findByIdAndDeletedAtIsNull(id)
                .map(CreateProductResponseDTO::fromProduct)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // Get All Products - excluding soft-deleted products
    public Page<Product> getAllProducts(int page, int size, String name, String category, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        Specification<Product> specification = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            specification = specification.and(FilterProductSpecifications.hasProductName(name));
        }

        if (category != null && !category.isEmpty()) {
            specification = specification.and(FilterProductSpecifications.hasCategoryName(category));
        }

        Page<Product> products = productRepository.findAll(specification, pageable);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found with the specified criteria");
        }

        return products;
    }

    // Soft Delete Product - mark the product as deleted without removing it from the database
    public void softDeleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setDeletedAt(OffsetDateTime.now());
        productRepository.save(product);
    }

    // Hard Delete Product - permanently remove the product from the database
    public void hardDeleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);
    }
}