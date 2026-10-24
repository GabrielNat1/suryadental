package br.com.suryadental.application.service;

import br.com.suryadental.application.dto.response.DashboardResponse;
import br.com.suryadental.application.repository.ClientRepository;
import br.com.suryadental.application.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {
    private final ClientRepository clientRepository;
    private final TicketRepository ticketRepository;

    public DashboardService(
            ClientRepository clientRepository,
            TicketRepository ticketRepository
    ) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        long totalClients = clientRepository.count();
        long totalTickets = ticketRepository.count();
        long openTickets = ticketRepository.countByClosedAtIsNull();
        long closedTickets = ticketRepository.countByClosedAtIsNotNull();

        return new DashboardResponse(
                totalClients,
                totalTickets,
                openTickets,
                closedTickets
        );
    }
}
