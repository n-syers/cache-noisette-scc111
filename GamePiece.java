import java.awt.Point;

public abstract class GamePiece {
    protected GameBoard gameBoard; // Reference to GameBoard for all pieces.
    
    /**
     * Retrieves an array of imageFilenames for a game piece.
     * @return imageFilenames picture array
     */
    public abstract Picture[] getPictures();

    /**
     * Retrieves an array of points for a game piece.
     * @return piecePositions point array
     */
    public abstract Point[] getPiecePositions();
    /**
     * Places GamePiece at specific cordinates given a direction
     * @param x The X-Cord on gameBoard
     * @param y The Y-Cord on gameBoard
     */
    public abstract void setPosition(int x, int y);
    /**
     * This function returns the point value of headPoint.
     * @return Point headPoint
     */
    public abstract Point getHeadPosition();

    /**
     * Determins whether a piece can be walked over or not. By default is true but might be overriden.
     * @return True by default
     */
    public abstract Boolean isWalkable();
    /**
     * Determines the size of the GamePiece returning the value as an int.
     * @return int of size.
     */
    public abstract int getSize();
}
