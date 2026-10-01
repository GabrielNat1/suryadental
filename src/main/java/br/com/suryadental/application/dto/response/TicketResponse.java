package br.com.suryadental.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketResponse {
    private Long id;

    private LocalDateTime openedAt;

    private String callAnalysis;
    private String subject;
    private String category;

    private ClientResponse client;

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