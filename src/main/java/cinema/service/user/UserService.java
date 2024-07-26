package cinema.service.user;

import cinema.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteByEmail(String email);
    Collection<User> findAll();
}
