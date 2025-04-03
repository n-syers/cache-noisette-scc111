import java.awt.Point;

public class Squirrel extends GamePiece{
    private Picture[] imageArray; // Array of Pictures used by the squirrel
    private Point[] piecePositions; // Position of each picture from head (Head picture is (0,0))
    private Point headPoint; // Position of the head on the board
    private Colour colour; // Colour of the squirrel
    private Direction direction; // Direction of the squirrel
    private Boolean hasNut = true; // Boolean for the squirrel holding nut

    /**
     * Constructor for squirrel class. Creates a new instance of the Squirrel class based on a colour in the given
     * direction.
     * @param colour The colour of the squirrel.
     * @param direction The direction the squirrel is facing.
     */
    public Squirrel(Colour colour, Direction direction, int x, int y){
        this.colour = colour;
        this.direction = direction;
        this.headPoint = new Point(x, y);
        int rotation = direction.getValue(direction);

        // set imageArray based on colour emun
        setPictures(colour, rotation);
        setPoints(colour, direction);
    }
    /**
     * This function initialises imageArray with Pictures based on a switch case for colour.
     * @param colour The colour of the squirrel.
     * @param rotation The rotation of the picture once squirrel colour is determined.
     */
    public void setPictures(Colour colour, int rotation){
        switch (colour) {
            case BLACK:
                imageArray = new Picture[4];
                imageArray[0] = new Picture("BlackSquirrel1", rotation);
                imageArray[1] = new Picture("BlackSquirrel1Nut", rotation);
                imageArray[2] = new Picture("BlackSquirrel2", rotation);
                imageArray[3] = new Picture("SquirrelFlower", rotation);
                break;

            case BROWN:
                imageArray = new Picture[4];
                imageArray[0] = new Picture("BrownSquirrel1", rotation);
                imageArray[1] = new Picture("BrownSquirrel1Nut", rotation);
                imageArray[2] = new Picture("BrownSquirrel2", rotation);
                imageArray[3] = new Picture("SquirrelFlower", rotation);
                break;

            case GREY:
                imageArray = new Picture[3];
                imageArray[0] = new Picture("GreySquirrel1", rotation);
                imageArray[1] = new Picture("GreySquirrel1Nut", rotation);
                imageArray[2] = new Picture("GreySquirrel2", rotation);
                break;

            case RED:
                imageArray = new Picture[3];
                imageArray[0] = new Picture("RedSquirrel1", rotation);
                imageArray[1] = new Picture("RedSquirrel1Nut", rotation);
                imageArray[2] = new Picture("RedSquirrel2", rotation);
                break;

            default:
                System.err.println("An error occurred: Unable to determin squirrel colour");
                break;
        }
    }

    /**
     * This function initialises piecePositions with Points based of direction and colour.
     * @param colour
     * @param direction
     */
    public void setPoints(Colour colour, Direction direction){
        switch (direction) {
            case NORTH:
                if (colour == Colour.BLACK) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(1,-1);
                } else if (colour == Colour.BROWN) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(1,0);
                } else {
                    piecePositions = new Point[2];
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(0,-1);
                break;
            case SOUTH:
                if (colour == Colour.BLACK) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(-1,1);
                } else if (colour == Colour.BROWN) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(-1,0);
                } else {
                    piecePositions = new Point[2];
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(0,1);
                break;
            case EAST:
                if (colour == Colour.BLACK) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(-1,-1);
                } else if (colour == Colour.BROWN) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(0,-1);
                } else {
                    piecePositions = new Point[2];
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(-1,0);
                break;
            case WEST:
                if (colour == Colour.BLACK) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(1,1);
                } else if (colour == Colour.BROWN) {
                    piecePositions = new Point[3];
                    piecePositions[2] = new Point(0,1);
                } else {
                    piecePositions = new Point[2];
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(1,0);
                break;
        
            default:
                System.err.println("An error occurred: Unable to determin squirrel points");
                break;
        }
    }
    /**
     * Checks whether the piece is allowed to move in the provided direction. Returns result as boolean.
     * @param direction The direction the piece wants to move (NORTH, SOUTH, EAST, WEST)
     * @return Boolean
     */
    public Boolean canMove(Direction direction){
        Boolean canMoveBool = false;
        return canMoveBool;
    }

    /**
     * Checks if move is legal using canMove(). If so, calls GameBoard to move piece in a direction.
     * @param direction The direction the piece wants to move (NORTH, SOUTH, EAST, WEST)
     */
    public void move(Direction direction){
        gameBoard.movePiece(this, direction);
    }

    /**
     * Returns a boolean value for if the squirrel has a nut (hasNut)
     * @return hasNut boolean.
     */
    public Boolean isCarryingNut(){
        return hasNut;
    }

    /**
     * Changes the head image to display without nut. Sets hasNut to false. Calls hasNut() in Hole class.
     * @param hole The hole that has hasNut() called.
     */
    public void dropNut(GamePiece hole){
        hasNut = false;
    }

    // Override for isWalkable to false.
    @Override  
    public Boolean isWalkable() {
        return false;
    }
}
