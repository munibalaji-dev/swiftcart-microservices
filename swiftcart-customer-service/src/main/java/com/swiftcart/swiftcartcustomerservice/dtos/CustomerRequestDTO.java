package com.swiftcart.swiftcartcustomerservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerRequestDTO {

    @Schema(description = "Customer full name", example = "Muni Balaji")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Customer email address", example = "munibalaji@gmail.com")
    @NotBlank(message = "Email is required")  //@Email alone does not make the field mandatory. An empty or null value is generally considered valid by @Email. That's why I combined both here.
    @Email(message = "Please enter a valid email address")
    private String email;

    @Schema(description = "Customer mobile number", example = "9090909090")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

}
