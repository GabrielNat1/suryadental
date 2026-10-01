package br.com.suryadental.application.service;

import br.com.suryadental.application.dto.request.TicketRequest;
import br.com.suryadental.application.dto.response.ClientResponse;
import br.com.suryadental.application.dto.response.TicketResponse;
import br.com.suryadental.application.exception.ClientNotFoundException;
import br.com.suryadental.application.exception.TicketNotFoundException;
import br.com.suryadental.application.model.Client;
import br.com.suryadental.application.model.Ticket;
import br.com.suryadental.application.repository.ClientRepository;
import br.com.suryadental.application.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;

    public TicketService(
            TicketRepository ticketRepository,
            ClientRepository clientRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.clientRepository = clientRepository;
    }

    public List<TicketResponse> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TicketResponse findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        return toResponse(ticket);
    }

    public TicketResponse create(TicketRequest request) {
        Ticket ticket = new Ticket();

        applyRequest(ticket, request);

        return toResponse(ticketRepository.save(ticket));
    }

    public TicketResponse update(Long id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        applyRequest(ticket, request);

        return toResponse(ticketRepository.save(ticket));
    }

    public void delete(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        ticketRepository.delete(ticket);
    }

    private void applyRequest(Ticket ticket, TicketRequest request) {
        ticket.setOpenedAt(request.getOpenedAt());
        ticket.setCallAnalysis(request.getCallAnalysis());
        ticket.setSubject(request.getSubject());
        ticket.setCategory(request.getCategory());

        if (request.getClientId() != null) {
            Client client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));

            ticket.setClient(client);
        } else {
            ticket.setClient(null);
        }

        ticket.setCreatedBy(request.getCreatedBy());
        ticket.setSalesStructure(request.getSalesStructure());
        ticket.setAgentFailure(request.getAgentFailure());
        ticket.setClientFailure(request.getClientFailure());
        ticket.setClosedAt(request.getClosedAt());
        ticket.setJustification(request.getJustification());
        ticket.setReason(request.getReason());
        ticket.setImproperReason(request.getImproperReason());
        ticket.setNfe(request.getNfe());
        ticket.setCollaboratorName(request.getCollaboratorName());
        ticket.setExchangeReturnReason(request.getExchangeReturnReason());
        ticket.setSellerName(request.getSellerName());
        ticket.setTicketNumber(request.getTicketNumber());
        ticket.setFirstResponsible(request.getFirstResponsible());
        ticket.setResolvedAt(request.getResolvedAt());
        ticket.setResponsible(request.getResponsible());
        ticket.setActionCount(request.getActionCount());
        ticket.setFailureResponsible(request.getFailureResponsible());
        ticket.setStatus(request.getStatus());
        ticket.setService(request.getService());
    }

    private TicketResponse toResponse(Ticket ticket) {
        ClientResponse clientResponse = null;

        if (ticket.getClient() != null) {
            Client client = ticket.getClient();

            clientResponse = new ClientResponse(
                    client.getId(),
                    client.getName(),
                    client.getClientCode()
            );
        }

        return new TicketResponse(
                ticket.getId(),
                ticket.getOpenedAt(),
                ticket.getCallAnalysis(),
                ticket.getSubject(),
                ticket.getCategory(),
                clientResponse,
                ticket.getCreatedBy(),
                ticket.getSalesStructure(),
                ticket.getAgentFailure(),
                ticket.getClientFailure(),
                ticket.getClosedAt(),
                ticket.getJustification(),
                ticket.getReason(),
                ticket.getImproperReason(),
                ticket.getNfe(),
                ticket.getCollaboratorName(),
                ticket.getExchangeReturnReason(),
                ticket.getSellerName(),
                ticket.getTicketNumber(),
                ticket.getFirstResponsible(),
                ticket.getResolvedAt(),
                ticket.getResponsible(),
                ticket.getActionCount(),
                ticket.getFailureResponsible(),
                ticket.getStatus(),
                ticket.getService()
        );
    }
}