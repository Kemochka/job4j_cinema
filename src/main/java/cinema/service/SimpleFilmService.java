package cinema.service;

import cinema.dto.FilmDto;
import cinema.model.Film;
import cinema.model.Genre;
import cinema.repository.FilmRepository;
import cinema.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository genreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = genreRepository;
    }
    @Override
    public Optional<Film> findByID(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Collection<FilmDto> getFilmList() {
        List<FilmDto> films = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            FilmDto filmDto = new FilmDto(film);
            Optional<Genre> genre = genreRepository.findById(film.getGenreId());
            genre.ifPresent(value -> filmDto.setGenre(value.getName()));
            films.add(filmDto);
        }
        return films;
    }
}
