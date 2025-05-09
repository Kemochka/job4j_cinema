package cinema.repository;

import cinema.configuration.DatasourceConfiguration;
import cinema.model.User;
import cinema.repository.user.Sql2oUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class
                .getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteByEmail(user.getEmail());
        }
    }

    @Test
    public void whenSaveAndGetSame() {
        var user = sql2oUserRepository.save(new User(0, "Ivan", "user1@mail.ru", "password")).get();
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSeveralUsersRegisteredThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(0, "Ivan", "123@321.ru", "password")).get();
        var user2 = sql2oUserRepository.save(new User(0, "Sergei", "123@322.ru", "password")).get();
        var user3 = sql2oUserRepository.save(new User(0, "Alex", "123@323.ru", "password")).get();
        var rsl = sql2oUserRepository.findAll();
        assertThat(rsl).isEqualTo(List.of(user1, user2, user3));
    }

    @Test
    public void whenDeleteThenGetOptionalEmpty() {
        var user1 = sql2oUserRepository.save(new User(0, "Ivan", "user1@mail.ru", "password")).get();
        var isDeleted = sql2oUserRepository.deleteByEmail(user1.getEmail());
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user1.getEmail(), user1.getPassword());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEqualTo(empty());
    }

    @Test
    public void whenEmailAlreadyExists() {
        var user1 = sql2oUserRepository.save(new User(0, "Ivan", "user1@mail.ru", "password"));
        var user2 = new User(0, "Ivan", "user1@mail.ru", "password");
        Optional<User> savedUser = sql2oUserRepository.save(user2);
        assertThat(savedUser).isEqualTo(empty());
    }
}