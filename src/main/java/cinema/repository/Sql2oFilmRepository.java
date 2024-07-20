package cinema.repository;

import cinema.model.Film;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Film> save(Film film) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
                    values(:name, :description, :year, :genreId, :age, :duration, :fileId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genre_id", film.getYear())
                    .addParameter("minimal_age", film.getAge())
                    .addParameter("duration_in_minutes", film.getDuration())
                    .addParameter("file_id", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return Optional.of(film);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Film film) {
        try (var connection = sql2o.open()) {
            var sql = """
                    update films 
                    set name = :name, 
                    description = :description, 
                    year = :year, 
                    genre_id = :genreId, 
                    minimal_age = :age, 
                    duration_in_minutes = :duration, 
                    file_id = :fileId
                    where id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genre_id", film.getYear())
                    .addParameter("minimal_age", film.getAge())
                    .addParameter("duration_in_minutes", film.getDuration())
                    .addParameter("file_id", film.getFileId());
            var affectedRows = query.setColumnMappings(Film.COLUMN_MAPPING).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean delete(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "delete from films where id = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "select * from films where id = :id");
            query.addParameter("id", id);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "select * from films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
