package br.com.suryadental.application.dto.response;

public record DashboardResponse(
        long totalClients,
        long totalTickets,
        long openTickets,
        long closedTickets
) {
}