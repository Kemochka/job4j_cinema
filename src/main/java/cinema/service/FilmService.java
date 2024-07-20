package cinema.service;

import cinema.dto.FilmDto;
import cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {
    Optional<Film> findByID(int id);

    Collection<Film> findAll();
    Collection<FilmDto> getFilmList();
}
