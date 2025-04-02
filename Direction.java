public enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    public final int rotationDegree;

    private Direction(int rotationDegree){
        this.rotationDegree = rotationDegree;
    }

    public int getValue(Direction direction){
        return direction.rotationDegree;
    }
}
