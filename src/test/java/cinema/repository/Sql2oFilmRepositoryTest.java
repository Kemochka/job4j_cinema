package cinema.repository;

import cinema.configuration.DatasourceConfiguration;
import cinema.model.File;
import cinema.model.Film;
import cinema.model.Genre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {
    private static Sql2oFilmRepository sql2oFilmRepository;

    private static Sql2oFileRepository sql2oFileRepository;

    private static Sql2oGenreRepository sql2oGenreRepository;

    private static Sql2oFilmSessionRepository sql2oSessionRepository;

    private static File file;

    private static Genre genre;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (InputStream inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oSessionRepository.findAll()
                .forEach(e -> sql2oSessionRepository.deleteById(e.getId()));
        sql2oFilmRepository.findAll()
                .forEach(e -> sql2oFilmRepository.delete(e.getId()));
        sql2oGenreRepository.findAll()
                .forEach(e -> sql2oGenreRepository.deleteById(e.getId()));
        file = sql2oFileRepository.save(new File("test", "test"));
        genre = sql2oGenreRepository.save(new Genre(1, "test")).get();
    }

    @AfterAll
    static void afterAll() {
        sql2oFilmRepository.findAll()
                .forEach(e -> sql2oFilmRepository.delete(e.getId()));
        sql2oFileRepository.deleteById(file.getId());
        sql2oGenreRepository.findAll()
                .forEach(e -> sql2oGenreRepository.deleteById(e.getId()));
    }

    @AfterEach
    public void clearFilms() {
        var films = sql2oFilmRepository.findAll();
        for (var film : films) {
            sql2oFilmRepository.delete(film.getId());
        }
    }

    @Test
    void whenSaveThenGetSame() {
        var testFilm = sql2oFilmRepository.save(new Film(0, "name", "description", 1, genre.getId(), 1, 1, file.getId())).get();
        var savedFilm = sql2oFilmRepository.findById(testFilm.getId()).get();
        assertThat(savedFilm).usingRecursiveComparison().isEqualTo(testFilm);
    }

    @Test
    void whenSaveSeveralThenGetAll() {
        var testFilm = sql2oFilmRepository.save(new Film(0, "name1", "description1", 1, genre.getId(), 1, 1, file.getId())).get();
        var testFilm2 = sql2oFilmRepository.save(new Film(0, "name2", "description2", 1, genre.getId(), 1, 1, file.getId())).get();
        var testFilm3 = sql2oFilmRepository.save(new Film(0, "name3", "description3", 1, genre.getId(), 1, 1, file.getId())).get();
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(testFilm, testFilm2, testFilm3));
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        var testFilm = sql2oFilmRepository.save(new Film(0, "name", "description", 1, genre.getId(), 1, 1, file.getId()));
        var isDeleted = sql2oFilmRepository.delete(testFilm.get().getId());
        var savedFilm = sql2oFilmRepository.findById(testFilm.get().getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedFilm).isNotPresent();
    }

    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oFilmRepository.delete(0)).isFalse();
    }
}