package model;

import java.util.List;

public class ResultOfFlipping {
    Position triggerPosition;
    List<Position> flippedPositions;
    Colors currentColor;

    public ResultOfFlipping(Position triggerPosition, List<Position> flippedPositions, Colors currentColor) {
        this.triggerPosition = triggerPosition;
        this.flippedPositions = flippedPositions;
        this.currentColor = currentColor;
    }

    public List<Position> getFlippedPositions() {
        return flippedPositions;
    }

    public Colors getColor(){
        return currentColor;
    }

    public Position getTriggerPosition() {
        return triggerPosition;
    }
}
