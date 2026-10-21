package br.com.suryadental.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImportResponse {

    private boolean success;
    private String message;
    private int rowsProcessed;
    private int clientsCreated;
    private int ticketsCreated;
    private int rowsWithErrors;
}
