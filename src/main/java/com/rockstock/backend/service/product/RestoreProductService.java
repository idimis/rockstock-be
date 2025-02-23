package com.rockstock.backend.service.product;

import com.rockstock.backend.entity.product.Product;
import com.rockstock.backend.infrastructure.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RestoreProductService {
    private final ProductRepository productRepository;

    public void restoreProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNotNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or not deleted"));

        product.setDeletedAt(null);
        productRepository.save(product);
    }
}
