import java.awt.Point;

public class GamePiece {
    protected GameBoard gameBoard; // Reference to GameBoard for all pieces.
    private int size; // size of the GamePiece on the board
    private Picture[] imageArray; // Array of Pictures
    private Point[] piecePositions; // Array of gamePiece positions.
    private Point headPoint; // Position of the head on the board
    
    /**
     * Retrieves an array of imageFilenames for a game piece.
     * @return imageFilenames picture array
     */
    public Picture[] getPictures(){
        return this.imageArray;
    }

    /**
     * Retrieves an array of points for a game piece.
     * @return piecePositions point array
     */
    public Point[] getPiecePositions(){
        return this.piecePositions;
    }
    /**
     * Places GamePiece at specific cordinates given a direction
     * @param x The X-Cord on gameBoard
     * @param y The Y-Cord on gameBoard
     * @param direction Direction enum for rotation of gamePiece
     */
    public void setPosition(int x, int y, Direction direction){
        headPoint = new Point(x, y);
        gameBoard.placePiece(this, x, y, direction, size);
    }
    /**
     * This function returns the point value of headPoint.
     * @return Point headPoint
     */
    public Point getHeadPosition(){
        return headPoint;
    }

    /**
     * Determins whether a piece can be walked over or not. By default is true but might be overriden.
     * @return True by default
     */
    public Boolean isWalkable(){
        return true; // Default response, can be overriden
    }
}
