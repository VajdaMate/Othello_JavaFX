package model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;

public class Disk {
    private final SimpleObjectProperty<Colors> color;

    public Disk(Colors color) {
        this.color = new SimpleObjectProperty<>(color);
    }

    public Disk() {
        this.color = new SimpleObjectProperty<>();
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
