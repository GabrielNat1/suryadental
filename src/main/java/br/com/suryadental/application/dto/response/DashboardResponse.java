package br.com.suryadental.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse{
        private long totalClients;
        private long totalTickets;
        private long openTickets;
        private long closedTickets;
}