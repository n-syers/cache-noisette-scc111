import java.awt.Point;

/**
 * This class represents a {@code hole} in the game, which extends the {@code GamePiece} abstract class.
 * This class defines the behaviour, attributes, and actions a hole instance can perform within
 * the game. 
 * 
 * @see GamePiece
 */
public class Hole extends GamePiece{
    private int size = 1; // size of the GamePiece on the board
    private Picture[] imageArray = new Picture[]{new Picture("assets\\icons\\Hole.png", 0), new Picture("assets\\icons\\HoleNut.png", 0)}; // Array of Pictures
    private Point[] piecePositions = new Point[]{new Point(0,0)}; // Array of gamePiece positions.
    private Point headPoint; // Position of the head on the board
    private Boolean hasNut = false; // If the hole has a nut, False by default

    /**
     * Determines whether the hole has a nut or not
     * @return {@code true} if {@code hasNut} is true, {@code false} otherwise.
     */
    public Boolean hasNut(){
        return hasNut;
    }
    /**
     * Sets {@code hasNut} to {@code true}. 
     */
    public void placeNut(){
        hasNut = true;
    }

    // Inherited Functions
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
        gameBoard.placePiece(this, x, y, size);
    }

    @Override
    public Point[] getPiecePositions(){
        return piecePositions;
    }

    @Override  
    public Boolean isWalkable() {
        return true;
    }
    @Override
    public int getSize() {
        return size;
    }
}
