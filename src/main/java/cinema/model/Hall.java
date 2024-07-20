package cinema.model;

import java.util.Map;
import java.util.Objects;

public class Hall {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "row_count", "row",
            "place_count", "place",
            "description", "description"
    );
    private int id;
    private String name;
    private int row;
    private int place;
    private String description;

    public Hall() {
    }

    public Hall(int id, String name, int row, int place, String description) {
        this.id = id;
        this.name = name;
        this.row = row;
        this.place = place;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return id == hall.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
