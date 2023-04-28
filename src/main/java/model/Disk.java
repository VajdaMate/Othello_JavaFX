package model;

public class Disk {
    private Position position;
    private Colors color;
    private boolean valid;

    public Disk(Position position, Colors color) {
        this.position = position;
        this.color=color;
        valid=false;
    }

    public Position getPosition() {
        return position;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public void setValid() {
        this.valid = true;
    }

    public boolean isValid() {
        return valid;
    }
}
