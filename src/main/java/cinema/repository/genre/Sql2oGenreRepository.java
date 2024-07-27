package cinema.repository.genre;

import cinema.model.Genre;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oGenreRepository implements GenreRepository {
    private final Sql2o sql2o;
    private static final Logger LOGGER = Logger.getLogger(Sql2oGenreRepository.class);

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Genre> save(Genre genre) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into genres(name) values(:name)
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("name", genre.getName());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            genre.setId(generatedId);
            return Optional.of(genre);
        } catch (Exception e) {
            LOGGER.error("Ошибка сохранения жанра", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("delete from genres where id = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from genres where id = :id");
            query.addParameter("id", id);
            var genre = query.executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }

    @Override
    public Collection<Genre> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from genres");
            return query.executeAndFetch(Genre.class);
        }
    }
}
