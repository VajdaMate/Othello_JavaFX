package model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;

/** Class representing a disk by a color.*/
public class Disk {
    /**The one information this class stores, the color of the disk.*/
    private final SimpleObjectProperty<Colors> color;

    /**
     * Instantiates a {@code Disk} with the specified color.
     * @param color {@link Colors} element
     */
    public Disk(Colors color) {
        this.color = new SimpleObjectProperty<>(color);
    }

    /** Empty Constructor need for Jackson ObjectMapper.*/
    public Disk() {
        this.color = new SimpleObjectProperty<>();
    }

    /** @return Color of the Disk*/
    public Colors getColor() {
        return color.get();
    }

    /**
     * Sets the color of the disk for the specified color.
     * @param color {@link Colors} color
     */
    public void setColor(Colors color) {
        this.color.set(color);
    }

    /** @return An Observable {@code SimpleObjectProperty<Colors>} which is need for the bindings*/
    public Observable colorProperty() {
        return color;
    }
}
