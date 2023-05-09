package model;

import java.util.ArrayList;
import java.util.List;

public class ResultOfFlipping {
    Position triggerPosition;
    List<Position> flippedPositions;
    Colors currentcolor;

    public ResultOfFlipping(Position triggerPosition, List<Position> flippedPositions, Colors currentcolor) {
        this.triggerPosition = triggerPosition;
        this.flippedPositions = flippedPositions;
        this.currentcolor = currentcolor;
    }

    public List<Position> getFlippedPositions() {
        return flippedPositions;
    }

    public Colors getColor(){
        return currentcolor;
    }

    public Position getTriggerPosition() {
        return triggerPosition;
    }
}
