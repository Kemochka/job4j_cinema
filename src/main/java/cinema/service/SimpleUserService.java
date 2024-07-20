package cinema.service;

import cinema.model.User;
import cinema.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }
}
