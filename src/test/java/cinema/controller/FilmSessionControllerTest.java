package cinema.controller;

import cinema.service.FilmSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FilmSessionControllerTest {
    FilmSessionController filmSessionController;
    FilmSessionService filmSessionService;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenGetFilmSessionListReturnFilmSessionList() {
        var model = new ConcurrentModel();
        var view = filmSessionController.getSessionList(model);
        assertThat(view).isEqualTo("filmSession/list");
    }
}