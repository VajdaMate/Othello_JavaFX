package model;

public class Disk {
    private Position position;
    boolean color;


    public Position getPosition() {
        return position;
    }

    public boolean isWhite() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
