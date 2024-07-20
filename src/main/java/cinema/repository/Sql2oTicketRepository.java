package cinema.repository;

import cinema.model.Ticket;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into tickets(session_id, row_number, place_number, user_id)
                    values(:sessionId, :row, :place, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRow())
                    .addParameter("place_number", ticket.getPlace())
                    .addParameter("user_id", ticket.getUserId());
            int generatedId = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    update tickets
                    set session_id = :session_id, row_number = :row, place_number = :place, user_id = :user_id
                    where id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRow())
                    .addParameter("place_number", ticket.getPlace())
                    .addParameter("user_id", ticket.getUserId());
            var affectedRows = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean delete(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("delete from tickets where id = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from tickets where id = :id");
            query.addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }
}
