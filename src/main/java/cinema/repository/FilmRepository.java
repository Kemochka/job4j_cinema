package cinema.repository;

import cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Optional<Film> save(Film film);

    boolean update(Film film);

    boolean delete(int id);

    Optional<Film> findById(int id);

    Collection<Film> findAll();
}
