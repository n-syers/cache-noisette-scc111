import java.awt.Point;

/**
 * This class represents a Hole GamePiece. Once an instance of this is created, it can
 * be used to manage and manipulate the GameBoard.
 */
public class Hole extends GamePiece{
    private int size = 1; // size of the GamePiece on the board
    private Picture[] imageArray; // Array of Pictures
    private Point[] piecePositions; // Array of gamePiece positions.
    private Point headPoint; // Position of the head on the board

    private Boolean hasNut = false; // If the hole has a nut, False by default
    /**
     * Constructor for Hole class. initiates piecePositions, headPoint, and imageArray with their respective values.
     * @param x The X-Cord for headPoint
     * @param y The Y-Cord for headPoint
     */
    public Hole(int x, int y){
        piecePositions = new Point[size];
        piecePositions[0] = new Point(0,0);
        headPoint = new Point(x, y);
        imageArray = new Picture[size+1];
        imageArray[0] = new Picture("assets/icons/Hole.png", 0);
        imageArray[1] = new Picture("assets/icons/HoleNut.png", 0);
    }
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
