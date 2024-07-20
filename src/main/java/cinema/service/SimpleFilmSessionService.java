package cinema.service;

import cinema.dto.SessionDto;
import cinema.model.Film;
import cinema.model.Hall;
import cinema.model.FilmSession;
import cinema.repository.FilmRepository;
import cinema.repository.HallRepository;
import cinema.repository.FilmSessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository sessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository sessionRepository, FilmRepository filmRepository, HallRepository hallRepository) {
        this.sessionRepository = sessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        return sessionRepository.findById(id);
    }

    @Override
    public Collection<FilmSession> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Collection<SessionDto> getSessionList() {
        List<SessionDto> sessions = new ArrayList<>();
        for (FilmSession session : sessionRepository.findAll()) {
            var filmOptional = filmRepository.findById(session.getFilmId());
            var hallOptional = hallRepository.findById(session.getHallId());
            if (filmOptional.isEmpty() || hallOptional.isEmpty()) {
                continue;
            }
            Film film = filmOptional.get();
            Hall hall = hallOptional.get();
            SessionDto sessionDto = new SessionDto(session, film, hall);
            sessions.add(sessionDto);
        }
        return sessions;
    }
}
