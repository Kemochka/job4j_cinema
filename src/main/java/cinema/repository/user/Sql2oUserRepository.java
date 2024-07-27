package cinema.repository.user;

import cinema.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;
    private static final Logger LOGGER = Logger.getLogger(Sql2oUserRepository.class);

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                    insert into users(full_name, email, password)
                    values (:name, :email, :password)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Ошибка сохранения пользователя");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var sql = """
                    select * from users where email = :email and password = :password
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            LOGGER.error("Невозможно найти пользователя по почте и паролю", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByEmail(String email) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "delete from users where email = :email"
            );
            query.addParameter("email", email);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select * from users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }
}
