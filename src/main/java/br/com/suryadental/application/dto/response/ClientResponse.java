package br.com.suryadental.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClientResponse {
    private UUID id;
    private String name;
    private String clientCode;
}
