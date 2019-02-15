package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Rank {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty points;

    public Rank(String fName, Integer lName) {
        this.username = new SimpleStringProperty(fName);
        this.points = new SimpleIntegerProperty(lName);
    }

    public String getUsername() {
        return username.get();
    }

    public Integer getPoints() {
        return points.get();
    }

}
