import java.awt.Point;

public class Squirrel extends GamePiece{
    private Picture[] imageArray; // Array of Pictures used by the squirrel
    private Point[] piecePositions; // Position of each picture from head (Head picture is (0,0))
    private int size; // Size of the piece on the board
    private Point headPoint; // Position of the head on the board
    private Boolean hasNut = true; // Boolean for the squirrel holding nut

    /**
     * Constructor for squirrel class. Creates a new instance of the Squirrel class based on a colour in the given
     * direction.
     * @param colour The colour of the squirrel.
     * @param direction The direction the squirrel is facing.
     */
    public Squirrel(Colour colour, Direction direction, int x, int y, int size){
        this.headPoint = new Point(x, y);
        this.size = size;
        int rotation = direction.getValue(direction);

        // Set the size of imageArray to 1 more than the size on board.
        imageArray = new Picture[size+1];

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
                imageArray[0] = new Picture("assets\\icons\\BlackSquirrel1.png", rotation);
                imageArray[1] = new Picture("assets\\icons\\BlackSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("assets\\icons\\BlackSquirrel2.png", rotation);
                imageArray[3] = new Picture("assets\\icons\\SquirrelFlower.png", rotation);
                break;

            case BROWN:
                imageArray[0] = new Picture("assets\\icons\\BrownSquirrel1.png", rotation);
                imageArray[1] = new Picture("assets\\icons\\BrownSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("assets\\icons\\BrownSquirrel2.png", rotation);
                imageArray[3] = new Picture("assets\\icons\\SquirrelFlower.png", rotation);
                break;

            case GREY:
                imageArray[0] = new Picture("assets\\icons\\GreySquirrel1.png", rotation);
                imageArray[1] = new Picture("assets\\icons\\GreySquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("assets\\icons\\GreySquirrel2.png", rotation);
                break;

            case RED:
                imageArray[0] = new Picture("assets\\icons\\RedSquirrel1.png", rotation);
                imageArray[1] = new Picture("assets\\icons\\RedSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("assets\\icons\\RedSquirrel2.png", rotation);
                break;

            default:
                System.err.println("An error occurred: Unable to determine squirrel colour");
                break;
        }
    }

    /**
     * This function initialises piecePositions with Points based of direction and colour.
     * @param colour
     * @param direction
     */
    public void setPoints(Colour colour, Direction direction){
        piecePositions = new Point[size];
        switch (direction) {
            case NORTH:
                if (colour == Colour.BLACK) {
                    piecePositions[2] = new Point(1,1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(1,0);
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(0,1);
                break;
            case SOUTH:
                if (colour == Colour.BLACK) {
                    piecePositions[2] = new Point(-1,-1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(-1,0);
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(0,-1);
                break;
            case EAST:
                if (colour == Colour.BLACK) {
                    piecePositions[2] = new Point(-1,-1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(0,-1);
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(-1,0);
                break;
            case WEST:
                if (colour == Colour.BLACK) {
                    piecePositions[2] = new Point(1,1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(0,1);
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
     * Returns a boolean value for if the squirrel has a nut (hasNut)
     * @return hasNut boolean.
     */
    public Boolean isCarryingNut(){
        return hasNut;
    }

    public Boolean canMove(int x, int y){
        GamePiece[][] gamePieceBoard = (GamePiece[][]) gameBoard.getPiecesBoard();
        Boolean isLegal = true;
        Point[] piecePoints = this.getPiecePositions();
        for (int i = 0; i < piecePoints.length; i++) {
            int newX = x + (int) piecePoints[i].getX();
            int newY = y + (int) piecePoints[i].getY();
            if (newX < 0 || newX >= 4 || newY < 0 || newY >= 4) {
                isLegal = false;
                break;
            }
    
            // Ensure the new position is not occupied by another game piece
            if (gamePieceBoard[newY][newX] != null) {
                if (gamePieceBoard[newY][newX] instanceof Flower || gamePieceBoard[newY][newX] instanceof Squirrel && gamePieceBoard[newY][newX] != this) {
                    isLegal = false;
                    break;
                }
            }
        }
        return isLegal;
    }
    public void move(Direction direction){
        int newX = 0;
        int newY = 0;
        switch (direction) {
            case NORTH:
                newX = (int) headPoint.getX();
                newY = -1 + (int) headPoint.getY();
                break;
            case SOUTH:
                newX = (int) headPoint.getX();
                newY = 1 + (int) headPoint.getY();
                break;
            case EAST: 
                newX = 1 + (int) headPoint.getX();
                newY = (int) headPoint.getY();
                break;
            case WEST:
                newX = -1 + (int) headPoint.getX();
                newY = (int) headPoint.getY();
                break;
            default:
                break;
        }
        if (!(canMove(newX, newY) == true)) {
            System.out.println("An Error Occured: Illegal Move!");
        }
        gameBoard.removePiece(this);
        setPosition(newX, newY);
        gameBoard.placePiece(this, newX, newY, size);
        gameBoard.checkNuts(newX, newY);
    }
    /**
     * Changes the head image to display without nut. Sets hasNut to false. Calls hasNut() in Hole class.
     * @param hole The hole that has placeNut() called.
     */
    public void dropNut(){
        hasNut = false;
        gameBoard.updateImageAt((int) headPoint.getX(), (int) headPoint.getY(), imageArray[0]);
    }
    
    // Inherited Functions
    @Override  
    public Boolean isWalkable() {
        return false;
    }

    @Override
    public Picture[] getPictures(){
        return imageArray;
    }

    @Override
    public Point getHeadPosition(){
        return headPoint;
    }

    @Override
    public void setPosition(int x, int y){
        headPoint = new Point(x, y);
    }

    @Override
    public Point[] getPiecePositions(){
        return piecePositions;
    }

    @Override
    public int getSize(){
        return size;
    }
}
