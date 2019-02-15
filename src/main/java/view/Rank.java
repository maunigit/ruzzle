package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Rank {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty points;

    public Rank(String user, Integer point) {
        this.username = new SimpleStringProperty(user);
        this.points = new SimpleIntegerProperty(point);
    }

    public String getUsername() {
        return username.get();
    }

    public Integer getPoints() {
        return points.get();
    }

}
