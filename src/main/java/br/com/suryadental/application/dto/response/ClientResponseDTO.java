package br.com.suryadental.application.dto.response;

import java.util.UUID;

public record ClientResponseDTO(
        UUID id,
        String name,
        String lastName,
        String address,
        String phone
)
{}
