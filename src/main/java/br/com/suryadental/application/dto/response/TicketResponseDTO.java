package br.com.suryadental.application.dto.response;

import java.time.LocalDateTime;

public record TicketResponseDTO(
        String id,
        String ticketNumber,
        String customerName,
        String category,
        String status,
        String description,
        String treatment,
        String assignedTo,

        LocalDateTime createAt,
        LocalDateTime resolvedAt
)
{}
