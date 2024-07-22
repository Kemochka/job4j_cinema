package cinema.repository;

import cinema.model.FilmSession;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<FilmSession> save(FilmSession session) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into film_sessions(film_id, halls_id, start_time, end_time, price)
                    values(:filmId, :hallsId, :start, :end, :price)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallsId", session.getHallId())
                    .addParameter("start", session.getStart())
                    .addParameter("end", session.getEnd())
                    .addParameter("price", session.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            session.setId(generatedId);
            return Optional.of(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(FilmSession session) {
        try (var connection = sql2o.open()) {
            var sql = """
                    update film_sessions
                    SET film_id = :film_id, halls_id = :halls_id, start_time = :start_time,
                    end_time = :end_time, price = :price
                    where id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallId", session.getHallId())
                    .addParameter("start", session.getStart())
                    .addParameter("end", session.getEnd())
                    .addParameter("price", session.getPrice());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "delete from film_sessions where id = :id"
            );
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "select * from film_sessions where id = :id"
            );
            query.addParameter("id", id);
            var session = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(session);
        }
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "select * from film_sessions"
            );
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }
}
