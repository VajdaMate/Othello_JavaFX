package model;

/**
 * Represents a Mathematical like vector for directions, except in the context of a Matrix.
 * Used in order to figure out which {@link Disk}-s are supposed to be flipped.
 */
public class DirectionVector {
    private final int xAxisDirection;
    private final int yAxisDirection;

    /**
     * Instantiates DirectionVector with x and y.
     * @param xAxisDirection Direction on the x Axis
     * @param yAxisDirection Direction on the y Axis
     */
    public DirectionVector(int xAxisDirection, int yAxisDirection) {
        this.xAxisDirection = xAxisDirection;
        this.yAxisDirection = yAxisDirection;
    }

    /** @return Direction on the x Axis*/
    public int xAxisDirection() {
        return xAxisDirection;
    }

    /** @return Direction on the y Axis*/
    public int yAxisDirection() {
        return yAxisDirection;
    }
}
