package cinema.repository;

import cinema.configuration.DatasourceConfiguration;
import cinema.model.Genre;
import cinema.repository.film.Sql2oFilmRepository;
import cinema.repository.film_session.Sql2oFilmSessionRepository;
import cinema.repository.genre.Sql2oGenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class Sql2oGenreRepositoryTest {
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oFilmSessionRepository sql2oSessionRepository;


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
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oSessionRepository = new Sql2oFilmSessionRepository(sql2o);

        sql2oSessionRepository.findAll()
                .forEach(e -> sql2oSessionRepository.deleteById(e.getId()));
        sql2oFilmRepository.findAll()
                .forEach(e -> sql2oFilmRepository.delete(e.getId()));
        sql2oGenreRepository.findAll()
                .forEach(e -> sql2oGenreRepository.deleteById(e.getId()));
    }

    @AfterEach
    public void clearGenres() {
        sql2oGenreRepository.findAll()
                .forEach(e -> sql2oGenreRepository.deleteById(e.getId()));
    }

    @Test
    void whenSaveThenGetSame() {
        var genre = sql2oGenreRepository.save(new Genre(0, "test")).get();
        var savedGenre = sql2oGenreRepository.findById(genre.getId()).get();
        assertThat(savedGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void whenSaveSeveralThenGetAll() {
        var genre1 = sql2oGenreRepository.save(new Genre(0, "test1")).get();
        var genre2 = sql2oGenreRepository.save(new Genre(0, "test2")).get();
        var genre3 = sql2oGenreRepository.save(new Genre(0, "test3")).get();
        var result = sql2oGenreRepository.findAll();
        assertThat(result).isEqualTo(List.of(genre1, genre2, genre3));
    }

    @Test
    void whenDoNotSaveThenNothingFound() {
        assertThat(sql2oGenreRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oGenreRepository.findById(0)).isNotPresent();
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        var genre = sql2oGenreRepository.save(new Genre(0, "test")).get();
        var isDeleted = sql2oGenreRepository.deleteById(genre.getId());
        var savedGenre = sql2oGenreRepository.findById(genre.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedGenre).isNotPresent();
    }

    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oGenreRepository.deleteById(0)).isFalse();
    }
}