package model;

/**Enum representing possible colors of the disks.*/
public enum Colors {
    /** Color representing an empty cell.*/
    NONE,
    /** Color representing a white {@link Disk}.*/
    WHITE,
    /** Color representing a black {@link Disk}.*/
    BLACK,
    /** Color representing a cell that is a valid move to the current player.*/
    VALID
}
