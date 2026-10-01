package br.com.suryadental.application.service;

import br.com.suryadental.application.exception.ApiException;
import br.com.suryadental.application.model.Ticket;
import br.com.suryadental.application.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ApiException("Ticket not found"));
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket update(Long id, Ticket ticket) {
        Ticket existingTicket = findById(id);
        // later

        return ticketRepository.save(existingTicket);
    }

    public void delete(Long id) {
        Ticket ticket = findById(id);
        ticketRepository.delete(ticket);
    }
}