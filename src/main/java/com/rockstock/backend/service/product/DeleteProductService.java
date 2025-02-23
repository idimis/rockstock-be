package com.rockstock.backend.service.product;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.entity.product.ProductStatus;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DeleteProductService {
    private final ProductRepository productRepository;

    public void softDeleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only draft products can be deleted");
        }

        product.setDeletedAt(OffsetDateTime.now());
        productRepository.save(product);
    }

    public void hardDeleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStatus() != ProductStatus.DRAFT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only draft products can be deleted");
        }

        productRepository.delete(product);
    }
}
