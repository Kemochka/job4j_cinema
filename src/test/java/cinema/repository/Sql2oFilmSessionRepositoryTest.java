package cinema.repository;

import cinema.configuration.DatasourceConfiguration;
import cinema.model.*;
import cinema.repository.file.Sql2oFileRepository;
import cinema.repository.film.Sql2oFilmRepository;
import cinema.repository.filmsession.Sql2oFilmSessionRepository;
import cinema.repository.genre.Sql2oGenreRepository;
import cinema.repository.hall.Sql2oHallRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import static org.assertj.core.api.Assertions.*;

class Sql2oFilmSessionRepositoryTest {
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oHallRepository sql2oHallRepository;
    private static Sql2oFilmSessionRepository sql2oSessionRepository;
    private static File file;
    private static Film film;
    private static Hall hall;
    private static Genre genre;
    private static final LocalDateTime START = LocalDateTime.now();
    private static final LocalDateTime END = START.plusHours(2);


    @BeforeAll
    static void initRepositories() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");

        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.databaseClient(dataSource);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oSessionRepository = new Sql2oFilmSessionRepository(sql2o);

        genre = sql2oGenreRepository.save(new Genre(1, "test")).get();
        file = sql2oFileRepository.save(new File("image.jpg", "path/file"));
        film = sql2oFilmRepository.save(
                new Film(0, "name", "description", 1, genre.getId(), 1, 1, file.getId())).get();
        hall = sql2oHallRepository.save(new Hall(1, "Main Hall", 20, 15, "Main cinema hall")).get();
        sql2oSessionRepository.findAll()
                .forEach(e -> sql2oSessionRepository.deleteById(e.getId()));
    }

    @AfterEach
    void deleteAllSessionsFromDatabaseTable() {
        sql2oSessionRepository.findAll()
                .forEach(e -> sql2oSessionRepository.deleteById(e.getId()));
    }

    @AfterAll
    static void afterAll() {
        sql2oFilmRepository.findAll()
                .forEach(e -> sql2oFilmRepository.delete(e.getId()));
        sql2oFileRepository.deleteById(file.getId());
        sql2oGenreRepository.findAll()
                .forEach(e -> sql2oGenreRepository.deleteById(e.getId()));
        sql2oHallRepository.findAll()
                .forEach(e -> sql2oHallRepository.deleteById(e.getId()));
    }

    @Test
    void whenSaveThenNewId() {
        int id = 0;
        FilmSession session = new FilmSession(id, film.getId(), hall.getId(), START, END, 100);
        FilmSession actual = sql2oSessionRepository.save(session).get();
        assertThat(actual.getId()).isGreaterThan(id);
    }

    @Test
    void whenSaveThenGetSame() {
        FilmSession session = new FilmSession(0, film.getId(), hall.getId(), START, END, 100);
        FilmSession expected = sql2oSessionRepository.save(session).get();
        Optional<FilmSession> actual = sql2oSessionRepository.findById(expected.getId());
        assertThat(actual).isEqualTo(Optional.of(expected));
    }

    @Test
    void whenSaveSeveralThenGetAll() {
        FilmSession session1 = new FilmSession(0, film.getId(), hall.getId(), START, END, 100);
        FilmSession session2 = new FilmSession(0, film.getId(), hall.getId(), START, END, 100);
        sql2oSessionRepository.save(session1);
        sql2oSessionRepository.save(session2);
        List<FilmSession> expected = List.of(session1, session2);
        Collection<FilmSession> actual = sql2oSessionRepository.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenNotSaveAnyThenGetEmptyOptional() {
        assertThat(sql2oSessionRepository.findById(1)).isEmpty();
    }

    @Test
    void whenNotSaveAnyThenEmptyCollection() {
        assertThat(sql2oSessionRepository.findAll()).isEmpty();
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        FilmSession session = new FilmSession(0, film.getId(), hall.getId(), START, END, 100);
        int id = sql2oSessionRepository.save(session).get().getId();
        boolean result = sql2oSessionRepository.deleteById(id);
        Optional<FilmSession> actual = sql2oSessionRepository.findById(id);
        assertThat(result).isTrue();
        assertThat(actual).isEmpty();
    }

    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oSessionRepository.deleteById(1)).isFalse();
    }
}