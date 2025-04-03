import java.awt.Point;

/**
 * This class represents a Hole GamePiece. Once an instance of this is created, it can
 * be used to manage and manipulate the GameBoard.
 */
public class Hole extends GamePiece{
    private int size = 1; // size of the GamePiece on the board
    private Picture[] imageArray = new Picture[]{new Picture("assets\\icons\\Hole.png", 0), new Picture("assets\\icons\\HoleNut.png", 0)}; // Array of Pictures
    private Point[] piecePositions = new Point[]{new Point(0,0)}; // Array of gamePiece positions.
    private Point headPoint; // Position of the head on the board
    private Boolean hasNut = false; // If the hole has a nut, False by default
    
    /**
     * Determine whether the hole has a nut or not
     * @return A Boolean value of hasNut
     */
    public Boolean hasNut(){
        return hasNut;
    }
    /**
     * Sets hasNut to true and updates image on gameBoard. 
     */
    public void placeNut(){
        hasNut = true;
        gameBoard.updateImageAt(this, (int) headPoint.getX(), (int) headPoint.getY(), null);
    }
}
