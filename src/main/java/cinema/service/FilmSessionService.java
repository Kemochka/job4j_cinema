package cinema.service;

import cinema.dto.SessionDto;
import cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {
    Optional<FilmSession> findById(int id);

    Collection<FilmSession> findAll();
    Collection<SessionDto> getSessionList();
}
