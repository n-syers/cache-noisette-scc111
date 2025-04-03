/**
 * This class represents a direction of pieces and movement. Once an instance of this is created, it can
 * be used to create GamePieces and movemeent buttons.
 */
public enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    public final int rotationDegree;

    /**
     * Constructor, assigns a value to each enum name
     * @param rotationDegree The value to assign to the enum
     */
    private Direction(int rotationDegree){
        this.rotationDegree = rotationDegree;
    }
    /**
     * Retrieves the value assigned to a enum name.
     * @param direction The enum name to retrieve value from.
     * @return The value of enum direction.
     */
    public int getValue(Direction direction){
        return direction.rotationDegree;
    }
    /**
     * Retrieves the enum name from the value that enum.
     * @param rotationDegree The value to find of enum.
     * @return Direction with value rotationDegree
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
