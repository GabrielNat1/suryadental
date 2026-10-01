package br.com.suryadental.application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime openedAt;

    private String callAnalysis;
    private String subject;
    private String category;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String createdBy;
    private String salesStructure;
    private String agentFailure;
    private String clientFailure;

    private LocalDateTime closedAt;

    private String justification;
    private String reason;
    private String improperReason;
    private String nfe;

    private String collaboratorName;
    private String exchangeReturnReason;
    private String sellerName;

    private String ticketNumber;

    private String firstResponsible;

    private LocalDateTime resolvedAt;

    private String responsible;

    private Integer actionCount;

    private String failureResponsible;

    private String status;

    private String service;
}