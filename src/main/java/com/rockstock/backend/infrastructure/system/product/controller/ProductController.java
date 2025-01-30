package com.rockstock.backend.infrastructure.system.product.controller;

import com.rockstock.backend.common.response.ApiResponse;
import com.rockstock.backend.common.response.PaginatedResponse;
import com.rockstock.backend.common.util.PaginationUtil;
import com.rockstock.backend.entity.Product;
import com.rockstock.backend.infrastructure.system.security.Claims;
import com.rockstock.backend.infrastructure.system.product.dto.CreateProductRequestDTO;
import com.rockstock.backend.infrastructure.system.product.dto.ProductDTO;
import com.rockstock.backend.infrastructure.usecase.product.dto.ProductStatisticsDTO;
import com.rockstock.backend.infrastructure.system.product.dto.UpdateProductRequestDTO;
import com.rockstock.backend.usecase.product.ProductService;
import com.rockstock.backend.infrastructure.usecase.review.dto.ReviewRequestDTO;
import com.rockstock.backend.infrastructure.usecase.review.dto.ReviewResponseDTO;
import com.rockstock.backend.infrastructure.usecase.review.service.ReviewService;
import com.rockstock.backend.infrastructure.usecase.transaction.dto.TransactionHistoryDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService,
                             ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        Long sellerId = Claims.getUserIdFromJwt();
        Product product = productService.createProduct(request, sellerId);
        return ApiResponse.successfulResponse("Create new product success", product);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller")
    public ResponseEntity<?> getAllProductsBySeller(@PageableDefault(size = 10) Pageable pageable) {
        Long sellerId = Claims.getUserIdFromJwt();
        Page<Product> products = productService.getAllProductsBySeller(pageable, sellerId);

        if (products.isEmpty()) {
            return ApiResponse.failedResponse(HttpStatus.NOT_FOUND.value(), "Products not found");
        }

        PaginatedResponse<Product> paginatedProducts = PaginationUtil.toPaginatedResponse(products);
        return ApiResponse.successfulResponse("Get all products by seller success", paginatedProducts);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequestDTO request) {
        Long sellerId = Claims.getUserIdFromJwt();
        Product product = productService.updateProduct(id, request, sellerId);
        return ApiResponse.successfulResponse("Update product success", product);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Long sellerId = Claims.getUserIdFromJwt();
        productService.deleteProduct(id, sellerId);
        return ApiResponse.successfulResponse("Delete product success", null);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/statistics")
    public ResponseEntity<?> getProductStatistics(@PageableDefault(size = 10) Pageable pageable) {
        Long sellerId = Claims.getUserIdFromJwt();
        Page<ProductStatisticsDTO> statistics = productService.getProductStatisticsBySeller(sellerId, pageable);
        PaginatedResponse<ProductStatisticsDTO> paginatedStatistics = PaginationUtil.toPaginatedResponse(statistics);
        return ApiResponse.successfulResponse("Get product statistics success", paginatedStatistics);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/transaction-history")
    public ResponseEntity<?> getTransactionHistoryBySeller(@PageableDefault(size = 10) Pageable pageable) {
        Long sellerId = Claims.getUserIdFromJwt();
        Page<TransactionHistoryDTO> transactionHistory = productService.getTransactionHistoryBySeller(sellerId, pageable);

        if (transactionHistory.isEmpty()) {
            return ApiResponse.failedResponse(HttpStatus.NOT_FOUND.value(), "Transaction history not found");
        }

        PaginatedResponse<TransactionHistoryDTO> paginatedTransactionHistory = PaginationUtil.toPaginatedResponse(transactionHistory);
        return ApiResponse.successfulResponse("Get transaction history success", paginatedTransactionHistory);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String search,
                                         @RequestParam(required = false, defaultValue = "false") boolean sortByNewest,
                                         @RequestParam(required = false, defaultValue = "false") boolean sortByHighestRating,
                                         @PageableDefault(size = 10) Pageable pageable) {

        Page<Product> products = productService.getAvailableProducts(pageable,
                category != null ? category.toLowerCase() : null,
                search != null ? search.toLowerCase() : null,
                sortByNewest,
                sortByHighestRating);

        if (products.isEmpty()) {
            return ApiResponse.failedResponse(HttpStatus.NOT_FOUND.value(), "Products not found");
        }

        PaginatedResponse<Product> paginatedAllProducts = PaginationUtil.toPaginatedResponse(products);
        return ApiResponse.successfulResponse("Get products success", paginatedAllProducts);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProductBySlug(@PathVariable String slug) {
        ProductDTO product = productService.getProductBySlug(slug);
        return ApiResponse.successfulResponse("Get product by slug success", product);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/review")
    public ResponseEntity<?> submitReview(@Valid @RequestBody ReviewRequestDTO reviewRequest) {
        Long customerId = Claims.getUserIdFromJwt();
        ReviewResponseDTO reviewResponse = productService.submitReview(customerId, reviewRequest);
        return ApiResponse.successfulResponse("Review submitted successfully", reviewResponse);
    }
}
