package model;

public class DirectionVector {
    private final int xAxisDirection;
    private final int yAxisDirection;

    public DirectionVector(int xAxisDirection, int yAxisDirection) {
        this.xAxisDirection = xAxisDirection;
        this.yAxisDirection = yAxisDirection;
    }

    public int xAxisDirection() {
        return xAxisDirection;
    }

    public int yAxisDirection() {
        return yAxisDirection;
    }
}
