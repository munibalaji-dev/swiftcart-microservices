package com.swiftcart.swiftcartcustomerservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerResponseDTO {

    @Schema(description = "Unique customer ID", example = "101")
    private Long id;

    @Schema(description = "Customer full name", example = "Muni Balaji")
    private String name;

    @Schema(description = "Customer email address", example = "munibalaji@gmail.com")
    private String email;

    @Schema(description = "Customer mobile number", example = "9090909090")
    private String phoneNumber;

    @Schema(description = "Customer registered date and time", example = "2026-11-06T13:42")
    private LocalDateTime createdAt;

    @Schema(description = "Customer lastUpdated date and time", example = "2026-11-06T13:42")
    private LocalDateTime updatedAt;
}
