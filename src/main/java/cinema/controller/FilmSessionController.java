package cinema.controller;

import cinema.service.FilmSessionService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ThreadSafe
@RequestMapping("/filmSessions")
public class FilmSessionController {
    private final FilmSessionService service;

    public FilmSessionController(FilmSessionService service) {
        this.service = service;
    }

    @GetMapping
    public String getSessionList(Model model) {
        model.addAttribute("filmSessions", service.getSessionList());
        return "filmSession/list";
    }
}
