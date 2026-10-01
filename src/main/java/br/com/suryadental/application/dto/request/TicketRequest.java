package br.com.suryadental.application.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TicketRequest {
        private LocalDateTime openedAt;

        private String callAnalysis;
        private String subject;
        private String category;

        private UUID clientId;

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