import java.awt.Point;

/**
 * This class represents a {@code squirrel} in the game, which extends the {@code GamePiece} abstract class.
 * This class defines the behaviour, attributes, and actions a squirrel instance can perform within
 * the game. 
 * 
 * @see GamePiece
 */
public class Squirrel extends GamePiece{
    private GameBoard gameBoard; // GameBoard reference. used to call functions.
    private Picture[] imageArray; // Array of Pictures used by the squirrel
    private Picture[] activePictures; // Current Pictures to use on GameBoard
    private Point[] piecePositions; // Position of each picture from head (Head picture is (0,0))
    private int size; // Size of the piece on the board
    private Point headPoint; // Position of the head on the board
    private Boolean hasNut = true; // Boolean for the squirrel holding nut

    /**
     * Enum to represent squirrel colours.
     */
    public enum Colour {
        BLACK,
        BROWN,
        GREY,
        RED
    }

    /**
     * Constructs a new instance of the {@code Squirrel} class with a specified {@code colour} enum and direction.
     * @param colour The colour enum of the squirrel.
     * @param direction The direction the squirrel is facing.
     * @param x The X coordinate of headPoint
     * @param y The Y coordinate of headPoint
     * @param size The size of the Squirrel (2 or 3)
     * @param gameBoard // The Existing GameBoard instance to reference
     */
    public Squirrel(Colour colour, Direction direction, int x, int y, int size, GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.headPoint = new Point(x, y);
        this.size = size;
        int rotation = direction.getValue(direction);

        // Set the size of imageArray to 1 more than the size on board.
        imageArray = new Picture[size+1];
        activePictures = new Picture[size];

        // set imageArray based on colour emun
        setPictures(colour, rotation);
        setPoints(colour, direction);
    }
    
    /**
     * Initialises {@code imageArray} and {@code activePictures} with {@code Pictures}
     * based on a switch case for colour.
     * @param colour The colour enum attached to the squirrel instance.
     * @param rotation The rotation in degrees the pictures should be displayed.
     */
    public void setPictures(Colour colour, int rotation){
        switch (colour) {
            case BLACK:
                imageArray[0] = new Picture("./assets/icons/BlackSquirrel1.png", rotation);
                imageArray[1] = new Picture("./assets/icons/BlackSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("./assets/icons/BlackSquirrel2.png", rotation);
                imageArray[3] = new Picture("./assets/icons/SquirrelFlower.png", rotation);
                activePictures[2] = imageArray[3];
                break;

            case BROWN:
                imageArray[0] = new Picture("./assets/icons/BrownSquirrel1.png", rotation);
                imageArray[1] = new Picture("./assets/icons/BrownSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("./assets/icons/BrownSquirrel2.png", rotation);
                imageArray[3] = new Picture("./assets/icons/SquirrelFlower.png", rotation);
                activePictures[2] = imageArray[3];
                break;

            case GREY:
                imageArray[0] = new Picture("./assets/icons/GreySquirrel1.png", rotation);
                imageArray[1] = new Picture("./assets/icons/GreySquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("./assets/icons/GreySquirrel2.png", rotation);
                break;

            case RED:
                imageArray[0] = new Picture("./assets/icons/RedSquirrel1.png", rotation);
                imageArray[1] = new Picture("./assets/icons/RedSquirrel1Nut.png", rotation);
                imageArray[2] = new Picture("./assets/icons/RedSquirrel2.png", rotation);
                break;

            default:
                System.err.println("An error occurred: Unable to determine squirrel colour");
                break;
        }
        activePictures[0] = imageArray[1];
        activePictures[1] = imageArray[2];
    }

    /**
     * Initialises {@code piecePositions} with {@code Points} based on squirrel direction and colour.
     * @param colour The colour enum attached to the squirrel instance.
     * @param direction The direction enum the squirrel is facing
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
                    piecePositions[2] = new Point(-1,1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(0,1);
                }
                piecePositions[0] = new Point(0,0);
                piecePositions[1] = new Point(-1,0);
                break;
            case WEST:
                if (colour == Colour.BLACK) {
                    piecePositions[2] = new Point(1,-1);
                } else if (colour == Colour.BROWN) {
                    piecePositions[2] = new Point(0,-1);
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
     * Returns a {@code boolean} indicating whether the squirrel has a nut.
     * @return {@code true} if the squirrel has a nut, {@code false} otherwise.
     */
    public Boolean isCarryingNut(){
        return hasNut;
    }

    /**
     * Determines if a {@code squirrel} instance can move in a given direction.
     * Checks if piece will collide with other {@code GamePieces} or will exceed
     * board (X,Y) limits.
     * @param x The new {@code headPoint X} coordinate.
     * @param y The new {@code headPoint Y} coordinate.
     * @return {@code true} if the move is legal, {@code false} otherwise.
     */
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
    /**
     * Moves an instance of {@code Squirrel} in the specified {@code direction}.
     * @param direction The {@code Direction} enum in which to move the {@code Squirrel}. 
     * @see Direction for possible movements. 
     */
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
        if (!(canMove(newX, newY))) {
            System.out.println("An Error Occured: Illegal Move!");
            return;
        }
        gameBoard.removePiece(this);
        setPosition(newX, newY);
        gameBoard.placePiece(this, newX, newY, size);
        gameBoard.checkNuts(newX, newY);
    }
    /**
     * Changes {@code activePictures[0]} to {@code imageArray[0]}, while also setting {@code hasNut} to false.
     */
    public void dropNut(){
        hasNut = false;
        activePictures[0] = imageArray[0];
    }
    
    // Inherited Functions
    @Override  
    public Boolean isWalkable() {
        return false;
    }

    @Override
    public Picture[] getPictures(){
        return activePictures;
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
