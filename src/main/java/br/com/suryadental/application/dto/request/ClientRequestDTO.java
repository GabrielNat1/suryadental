package br.com.suryadental.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "lastName is required")
        String lastName,

        @NotBlank(message = "address is required")
        String address,

        @NotBlank(message = "phone is required")
        String phone
)
{}