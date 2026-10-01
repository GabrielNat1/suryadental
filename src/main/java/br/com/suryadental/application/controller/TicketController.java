//package br.com.suryadental.application.controller;
//
//import br.com.suryadental.application.model.Ticket;
//import br.com.suryadental.application.repository.TicketRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/ticket")
//public class TicketController {
//    private final TicketRepository ticketRepository;
//
//    public TicketController(TicketRepository ticketRepository) {
//        this.ticketRepository = ticketRepository;
//    }
//
//    @GetMapping
//    public List<Ticket> findAll() {
//        return ticketRepository.findAll();
//    }
//
//    @PostMapping
//    public Ticket create(@RequestBody Ticket ticket) {
//        return ticketRepository.save(ticket);
//    }
//}
