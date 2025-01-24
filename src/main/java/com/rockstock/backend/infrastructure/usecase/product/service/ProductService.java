package com.rockstock.backend.infrastructure.usecase.product.service;

import com.rockstock.backend.entity.Product;
import com.rockstock.backend.entity.Review;
import com.rockstock.backend.entity.User;
import com.rockstock.backend.infrastructure.usecase.product.dto.CreateProductRequestDTO;
import com.rockstock.backend.infrastructure.usecase.product.dto.ProductDTO;
import com.rockstock.backend.infrastructure.usecase.product.dto.ProductStatisticsDTO;
import com.rockstock.backend.infrastructure.usecase.product.dto.UpdateProductRequestDTO;
import com.rockstock.backend.infrastructure.usecase.product.repository.ProductRepository;
import com.rockstock.backend.infrastructure.usecase.review.dto.ReviewRequestDTO;
import com.rockstock.backend.infrastructure.usecase.review.dto.ReviewResponseDTO;
import com.rockstock.backend.infrastructure.usecase.review.repository.ReviewRepository;
import com.rockstock.backend.infrastructure.usecase.ticket.repository.TicketRepository;
import com.rockstock.backend.infrastructure.usecase.transaction.dto.TransactionHistoryDTO;
import com.rockstock.backend.infrastructure.usecase.transaction.repository.TransactionRepository;
import com.rockstock.backend.infrastructure.usecase.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {

    private final TicketRepository ticketRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public ProductService(ProductRepository productRepository,
                          UserRepository userRepository,
                          TicketRepository ticketRepository,
                          ReviewRepository reviewRepository,
                          TransactionRepository transactionRepository) {
        this.ticketRepository = ticketRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Product createProduct(CreateProductRequestDTO request, Long organizerId) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        Product product = new Product();
        product.setImageUrl(request.getImageUrl());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setDateTimeStart(request.getDateTimeStart());
        product.setDateTimeEnd(request.getDateTimeEnd());
        product.setLocation(request.getLocation());
        product.setLocationDetails(request.getLocationDetails());
        product.setCategory(request.getCategory());
        product.setFee(request.getFee());
        product.setAvailableSeats(request.getAvailableSeats());
        product.setOrganizer(organizer);

        product.generateSlug();

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, UpdateProductRequestDTO request, Long organizerId) {
        Product product = productRepository.findByIdAndOrganizerId(productId, organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found or not owned by organizer"));

        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
        if (request.getTitle() != null) {
            product.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getDateTimeStart() != null) {
            product.setDateTimeStart(request.getDateTimeStart());
        }
        if (request.getDateTimeEnd() != null) {
            product.setDateTimeEnd(request.getDateTimeEnd());
        }
        if (request.getLocation() != null) {
            product.setLocation(request.getLocation());
        }
        if (request.getLocationDetails() != null) {
            product.setLocationDetails(request.getLocationDetails());
        }
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        if (request.getFee() != null) {
            product.setFee(request.getFee());
        }
        if (request.getAvailableSeats() != null) {
            product.setAvailableSeats(request.getAvailableSeats());
        }

        product.generateSlug();

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId, Long organizerId) {
        Product product = productRepository.findByIdAndOrganizerId(productId, organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found or not owned by organizer"));
        productRepository.delete(product);
    }

    public Page<Product> getAllProductsByOrganizer(Pageable pageable, Long organizerId) {
        return productRepository.findByOrganizerId(pageable, organizerId);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getOrganizer().getId(),
                product.getImageUrl(),
                product.getTitle(),
                product.getDescription(),
                product.getDateTimeStart(),
                product.getDateTimeEnd(),
                product.getLocation(),
                product.getLocationDetails(),
                product.getCategory(),
                product.getFee(),
                product.getAvailableSeats(),
                product.getBookedSeats(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getDeletedAt()
        );
    }

    public Page<Product> getProductsExcludingLocation(Pageable pageable, String location) {
        return productRepository.findByLocationNot(pageable, location);
    }

    public Page<Product> getUpcomingProducts(Pageable pageable, String location, String category, String search, boolean sortByNewest, boolean sortByHighestRating) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (sortByHighestRating) {
            return productRepository.findUpcomingProductsSortedByHighestRating(pageable, currentDateTime, location, category, search);
        } else if (sortByNewest) {
            return productRepository.findUpcomingProductsSortedByNewest(pageable, currentDateTime, location, category, search);
        } else {
            return productRepository.findUpcomingProducts(pageable, currentDateTime, location, category, search);
        }
    }

    @Transactional
    public ReviewResponseDTO submitReview(Long customerId, ReviewRequestDTO reviewRequest) {
        if (!ticketRepository.existsByCustomerIdAndProductId(customerId, reviewRequest.getProductId())) {
            throw new IllegalArgumentException("Customer has not bought a ticket for this product");
        }

        Product product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getDateTimeEnd().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Product has not ended yet");
        }

        if (reviewRepository.existsByCustomerIdAndProductId(customerId, reviewRequest.getProductId())) {
            throw new IllegalArgumentException("Customer has already submitted a review for this product");
        }

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Review review = new Review();
        review.setCustomerId(customerId);
        review.setProductId(product.getId());
        review.setRating(reviewRequest.getRating());
        review.setReview(reviewRequest.getReview());
        review = reviewRepository.save(review);

        ReviewResponseDTO responseDTO = new ReviewResponseDTO();
        responseDTO.setId(review.getId());
        responseDTO.setCustomerId(review.getCustomerId());
        responseDTO.setProductId(review.getProductId());
        responseDTO.setRating(review.getRating());
        responseDTO.setReview(review.getReview());
        responseDTO.setCreatedAt(review.getCreatedAt());
        responseDTO.setProductTitle(product.getTitle());
        responseDTO.setProductDateTimeStart(product.getDateTimeStart());
        responseDTO.setProductDateTimeEnd(product.getDateTimeEnd());
        responseDTO.setCustomerName(customer.getName());

        return responseDTO;
    }

    public Page<Product> getPastProductsByCustomer(Long customerId, Pageable pageable) {
        return productRepository.findPastProductsByCustomer(customerId, LocalDateTime.now(), pageable);
    }

    public Page<Product> getUpcomingProductsByCustomer(Long customerId, Pageable pageable) {
        return productRepository.findUpcomingProductsByCustomer(customerId, LocalDateTime.now(), pageable);
    }

    public Page<ProductStatisticsDTO> getProductStatisticsByOrganizer(Long organizerId, Pageable pageable) {
        return productRepository.findProductStatisticsByOrganizer(organizerId, pageable);
    }

    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return convertToDTO(product);
    }

    public Page<Product> getHottestUpcomingProduct(Pageable pageable) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return productRepository.findHottestUpcomingProduct(currentDateTime, pageable);
    }

    public Page<TransactionHistoryDTO> getTransactionHistoryByOrganizer(Long organizerId, Pageable pageable) {
        return transactionRepository.findTransactionHistoryByOrganizer(organizerId, pageable);
    }
}
