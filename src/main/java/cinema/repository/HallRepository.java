package cinema.repository;

import cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

public interface HallRepository {
    Optional<Hall> save(Hall hall);

    boolean update(Hall hall);

    boolean deleteById(int id);

    Optional<Hall> findById(int id);

    Collection<Hall> findAll();
}
