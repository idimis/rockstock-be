package com.rockstock.backend.usecase.user.dto;

import com.rockstock.backend.common.response.PaginatedResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPublicDetailsDTO {

    private Long userId;
    private String photoProfileUrl;
    private String fullName;
    private String email; // Optional or admin-only access
    private String website;
    private PaginatedResponse events;
}
