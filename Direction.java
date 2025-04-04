/**
 * This enum represents a direction a {@code GamePieces} could have.
 * Each direction has a corrisponding int {@code rotation} value.
 */
public enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    public final int rotationDegree; // The rotation value associated with the enum name

    /**
     * Constructs the direction by assigning {@code rotationDegree} the specified value.
     * @param rotationDegree The value associated with the enum name.
     */
    private Direction(int rotationDegree){
        this.rotationDegree = rotationDegree;
    }
    /**
     * Retrieves the {@code rotationDegree} of the {@code Direction} instance.
     * @param direction The enum name to retrieve the value.
     * @return {@code rotationDegree} assigned to the {@code direction}
     */
    public int getValue(Direction direction){
        return direction.rotationDegree;
    }
    /**
     * Retrieves the enum name given a value.
     * @param rotationDegree The value of the enum name to find.
     * @return Returns {@code direction} enum with {@code rotationDegree}.
     */
    public static Direction getDirection(int rotationDegree){
        Direction direction = Direction.NORTH;
        switch (rotationDegree) {
            case 0:
                direction = Direction.NORTH;
                break;
            case 180:
                direction = Direction.SOUTH;
                break;
            case 90:
                direction = Direction.EAST;
                break;
            case 270:
                direction = Direction.WEST;
                break;        
            default:
                System.err.println("Could not find direction with rotation " + rotationDegree);
                break;
        }
        return direction;
    }
}
