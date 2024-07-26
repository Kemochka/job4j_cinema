package cinema.controller;

import cinema.service.film.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FilmControllerTest {
    FilmController filmController;
    FilmService filmService;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenGetFilmsPageReturnFilmListView() {
        var model = new ConcurrentModel();
        var view = filmController.getFilmsPage(model);
        assertThat(view).isEqualTo("film/list");
    }
}