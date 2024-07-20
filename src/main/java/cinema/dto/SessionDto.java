package cinema.dto;

import cinema.model.Film;
import cinema.model.Hall;
import cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SessionDto {
    private int id;
    private String film;
    private String hall;
    private int row;
    private int place;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;
    private int sessionId;

    public SessionDto() {
    }

    public SessionDto(int id, String film, String hall, int row, int place, LocalDateTime start, LocalDateTime end, int price, int sessionId) {
        this.id = id;
        this.film = film;
        this.hall = hall;
        this.row = row;
        this.place = place;
        this.start = start;
        this.end = end;
        this.price = price;
        this.sessionId = sessionId;
    }

    public SessionDto(FilmSession session, Film film, Hall hall) {
        this.film = film.getName();
        this.hall = hall.getName();
        this.row = hall.getRow();
        this.place = hall.getPlace();
        this.start = session.getStart();
        this.end = session.getEnd();
        this.price = session.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Collection<Integer> getPlaceList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= this.place; i++) {
            list.add(i);
        }
        return list;
    }

    public Collection<Integer> getRowList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= this.row; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionDto that = (SessionDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
