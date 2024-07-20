package cinema.service;

import cinema.dto.FileDto;
import cinema.model.File;

import java.util.Optional;

public interface FileService {
    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    void deleteById(int id);
}
