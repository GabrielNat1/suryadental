package br.com.suryadental.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TicketRequestDTO(
        @NotBlank(message = "Name is required.")
        String customerName,

        @NotBlank(message = "Category is required.")
        String category,

        @NotBlank(message = "Status is required.")
        String status,

        @NotBlank(message = "description is required")
        String description,

        String treatment,
        String assignedTo
)
{}