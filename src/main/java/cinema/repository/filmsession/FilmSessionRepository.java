package cinema.repository.filmsession;

import cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionRepository {
    Optional<FilmSession> save(FilmSession session);

    boolean update(FilmSession session);

    boolean deleteById(int id);

    Optional<FilmSession> findById(int id);

    Collection<FilmSession> findAll();
}
