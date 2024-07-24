package cinema.repository;

import cinema.model.Hall;
import cinema.model.FilmSession;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oHallRepository implements HallRepository {
    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Hall> save(Hall hall) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into halls(name, row_count, place_count, description)
                    values(:name, :row, :place, :description)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", hall.getName())
                    .addParameter("row", hall.getRow())
                    .addParameter("place", hall.getPlace())
                    .addParameter("description", hall.getDescription());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            hall.setId(generatedId);
            return Optional.of(hall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Hall hall) {
        try (var connection = sql2o.open()) {
            var sql = """
                    update halls
                    set name = :name, row_count = :row, place_count = :place, description = :description
                    where id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("name", hall.getName())
                    .addParameter("row", hall.getRow())
                    .addParameter("place", hall.getPlace())
                    .addParameter("description", hall.getDescription())
                    .addParameter("id", hall.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("delete from halls where id = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (var connection = sql2o.open()) {
            var sql = """
                    select * from halls where id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("id", id);
            var hall = query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }

    @Override
    public Collection<Hall> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from halls");
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
        }
    }
}
