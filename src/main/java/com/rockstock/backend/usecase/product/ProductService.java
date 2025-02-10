package com.rockstock.backend.usecase.product;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.product.dto.CreateProductRequestDTO;
import com.rockstock.backend.infrastructure.product.dto.CreateProductResponseDTO;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Create Product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get All Products (dengan totalStocks dihitung otomatis)
    public List<CreateProductResponseDTO> getAllProducts() {
        return productRepository.findAllByDeletedAtIsNull().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get Product By ID
    public CreateProductResponseDTO getProductById(Long id) {
        return productRepository.findByIdAndDeletedAtIsNull(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // Update Product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setProductName(productDetails.getProductName());
        product.setDetail(productDetails.getDetail());
        product.setPrice(productDetails.getPrice());
        product.setWeight(productDetails.getWeight());

        return productRepository.save(product);
    }

    // Soft Delete Product
    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setDeletedAt(OffsetDateTime.now());
        productRepository.save(product);
    }

    private CreateProductResponseDTO convertToDTO(Product product) {
        return new CreateProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getDetail(),
                product.getPrice(),
                product.getWeight(),
                product.getTotalStocks()
        );
    }
}