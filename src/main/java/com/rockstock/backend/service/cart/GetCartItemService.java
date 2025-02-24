package com.rockstock.backend.service.cart;

import com.rockstock.backend.infrastructure.cart.dto.GetCartItemResponseDTO;

import java.util.List;
import java.util.Optional;

public interface GetCartItemService {
    List<GetCartItemResponseDTO> getAllByActiveCartId();
    Optional<GetCartItemResponseDTO> getByActiveCartIdAndId(Long cartItemId);
    Optional<GetCartItemResponseDTO> getByActiveCartIdAndProductId(Long productId);
    Optional<GetCartItemResponseDTO> getByActiveCartIdAndProductName(String name);
}
