package br.com.suryadental.application.repository;

import br.com.suryadental.application.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}
