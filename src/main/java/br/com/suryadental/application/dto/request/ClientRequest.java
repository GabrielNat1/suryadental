package br.com.suryadental.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {
        private String name;
        private String clientCode;
}