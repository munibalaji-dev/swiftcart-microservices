package com.swiftcart.swiftcartcustomerservice.dtos;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerUpdateDTO {

    private String name;
    @Email(message = "please enter a valid email") // why I dont use any other annotations like @NotBlank for name is here the fields are optional user wants to select which field to change.
    private String email;
    private String phoneNumber;
}
