package model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;

public class Disk {
    private final Position position;
    private SimpleObjectProperty<Colors> color;

    public Disk(Position position, Colors color) {
        this.position = position;
        this.color = new SimpleObjectProperty<>(color);
    }

    public Position getPosition() {
        return position;
    }

    public Colors getColor() {
        return color.get();
    }

    public void setColor(Colors color) {
        this.color.set(color);
    }

    public Observable colorProperty() {
        return color;
    }



}
