package cinema.service.ticket;

import cinema.model.Ticket;
import cinema.repository.ticket.TicketRepository;
import cinema.service.file.FileService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {
    private TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository, FileService fileService) {
        this.ticketRepository = sql2oTicketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public boolean update(Ticket ticket) {
        return ticketRepository.update(ticket);
    }

    @Override
    public boolean delete(int id) {
        var ticketOptional = findById(id);
        if (ticketOptional.isEmpty()) {
            return false;
        }
        var isDeleted = ticketRepository.delete(id);
        return isDeleted;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
