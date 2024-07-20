package cinema.dto;

import cinema.model.Film;

import java.util.Objects;

public class FilmDto {
    private int id;
    private String name;
    private String description;
    private String year;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;
    private String genre;

    public FilmDto(int id, String name, String description, String year, int minimalAge, int durationInMinutes, int fileId, String genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.fileId = fileId;
        this.genre = genre;
    }

    public FilmDto(Film film) {
        this.id = film.getId();
        this.name = film.getName();
        this.description = film.getDescription();
        this.year = film.getYear();
        this.minimalAge = film.getAge();
        this.durationInMinutes = film.getDuration();
        this.fileId = film.getFileId();
    }

    public FilmDto(Film film, String genre) {
        this.id = film.getId();
        this.name = film.getName();
        this.description = film.getDescription();
        this.year = film.getYear();
        this.minimalAge = film.getAge();
        this.durationInMinutes = film.getDuration();
        this.fileId = film.getFileId();
        this.genre = genre;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
