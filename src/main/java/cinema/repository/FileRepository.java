package cinema.repository;

import cinema.model.File;

import java.util.Optional;

public interface FileRepository {
    File save(File file);

    Optional<File> findById(int id);

    void deleteById(int id);
}
