package model;

import java.util.List;

/**Class representing the results of Flipping, in order to be able to revert them.*/
public class ResultOfFlipping {
    Position triggerPosition;
    List<Position> flippedPositions;
    Colors currentColor;

    /**
     * Instantiates {@code ResultsOfFlipping} with the given parameters.
     * @param triggerPosition The position triggering the flip
     * @param flippedPositions List of positions that got flipped
     * @param currentColor The color disks flipped to
     */
    public ResultOfFlipping(Position triggerPosition, List<Position> flippedPositions, Colors currentColor) {
        this.triggerPosition = triggerPosition;
        this.flippedPositions = flippedPositions;
        this.currentColor = currentColor;
    }

    /** @return List of flipped positions*/
    public List<Position> getFlippedPositions() {
        return flippedPositions;
    }

    /** @return The color disks have flipped to*/
    public Colors getColor(){
        return currentColor;
    }

    /** @return The position that triggered the flip */
    public Position getTriggerPosition() {
        return triggerPosition;
    }
}
