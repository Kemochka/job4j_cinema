package cinema.service;

import cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> save(Ticket ticket);

    boolean update(Ticket ticket);

    boolean delete(int id);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findAll();
}
