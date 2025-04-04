import java.awt.Point;
/**
 * This abstract class represents a {@code GamePiece} in the game.
 * This abstract class defines the common behaviour, attributes, and actions of all {@code GamePiece} subclasses.
 * 
 * @see Squirrel
 * @see Hole
 * @see Flower
 */
public abstract class GamePiece {
    protected GameBoard gameBoard; // Reference to GameBoard for all pieces.
    
    /**
     * Retrieves an array of type {@code Picture} of a {@code GamePiece} instance.
     * @return Returns an array of type {@code Picture}.
     */
    public abstract Picture[] getPictures();

    /**
     * Retrieves an array of type {@code Point} of a {@code GamePiece} instance.
     * @return Returns an array of type {@code Point}.
     */
    public abstract Point[] getPiecePositions();
    /**
     * Sets the {@code headPoint} of the {@code GamePiece} instance to specific cordinates.
     * @param x The X-Cord on gameBoard.
     * @param y The Y-Cord on gameBoard.
     */
    public abstract void setPosition(int x, int y);
    /**
     * Returns the {@code Point} value of {@code headPoint} variable of GamePiece.
     * @return Returns a {@code Point}.
     */
    public abstract Point getHeadPosition();

    /**
     * Determines whether a piece can be walked over or not.
     * @return Returns {@code true} if piece can be walked over, {@code false} otherwise.
     */
    public abstract Boolean isWalkable();
    /**
     * Determines the size of the {@code GamePiece}, returning the value as an int.
     * @return An integer representing the size of the GamePiece.
     */
    public abstract int getSize();
}
