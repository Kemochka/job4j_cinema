package cinema.controller;

import cinema.model.Ticket;
import cinema.service.film_session.FilmSessionService;
import cinema.service.ticket.TicketService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@ThreadSafe
@RequestMapping("/ticket")
public class TicketController {
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    public TicketController(FilmSessionService filmSessionService, TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/buy/{id}")
    public String getCreationPage(Model model, @PathVariable int id) {
        var filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("Сессия не найдена");
            return "errors/404";
        }
        var filmSession = filmSessionOptional.get();
        model.addAttribute("sessionId", id);
        model.addAttribute("row", filmSession.getRowList());
        model.addAttribute("place", filmSession.getPlaceList());
        model.addAttribute("filmSession", filmSession);
        return "ticket/buy";
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model) {
        Optional<Ticket> savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            return "ticket/error";
        }
        model.addAttribute("ticket", ticket);
        return "ticket/success";
    }
}
