package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The Rank class that allows to display the ranking data appropriately.
 */
public class Rank {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty points;

    public Rank(String user, Integer point) {
        this.username = new SimpleStringProperty(user);
        this.points = new SimpleIntegerProperty(point);
    }

    /**
     * Get the username of the person.
     *
     * @return
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * Get the points of the person.
     *
     * @return
     */
    public Integer getPoints() {
        return points.get();
    }

}
