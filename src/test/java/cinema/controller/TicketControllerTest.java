package cinema.controller;

import cinema.model.Ticket;
import cinema.service.FilmSessionService;
import cinema.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    TicketController ticketController;
    TicketService ticketService;
    FilmSessionService filmSessionService;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(filmSessionService, ticketService);
    }

    @Test
    public void whenBuyTicketAndReturnSuccessPage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket, model);
        assertThat(view).isEqualTo("ticket/success");
    }

    @Test
    public void whenBuyTicketAndReturnErrorPage() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(any())).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket, model);
        assertThat(view).isEqualTo("ticket/error");
    }
}