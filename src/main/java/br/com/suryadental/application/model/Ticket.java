package br.com.suryadental.application.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;

    private String ticketNumber;
    private String customerName;
    private String category;
    private String status;
    private String description;
    private String treatment;
    private String assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}
