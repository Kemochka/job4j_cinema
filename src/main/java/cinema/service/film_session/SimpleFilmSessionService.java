package cinema.service.film_session;

import cinema.dto.SessionDto;
import cinema.model.Film;
import cinema.model.Hall;
import cinema.model.FilmSession;
import cinema.repository.film.FilmRepository;
import cinema.repository.hall.HallRepository;
import cinema.repository.film_session.FilmSessionRepository;
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
    public Optional<SessionDto> findById(int id) {
        var filmSessionOptional = sessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        FilmSession filmSession = filmSessionOptional.get();
        var filmOptional = filmRepository.findById(filmSession.getFilmId());
        var hallOptional = hallRepository.findById(filmSession.getHallId());
        if (filmOptional.isEmpty() || hallOptional.isEmpty()) {
            return Optional.empty();
        }
        Film film = filmOptional.get();
        Hall hall = hallOptional.get();
        SessionDto sessionDto = new SessionDto(filmSession, film, hall);
        return Optional.of(sessionDto);
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
            sessionDto.setId(session.getId());
            sessions.add(sessionDto);
        }
        return sessions;
    }
}
