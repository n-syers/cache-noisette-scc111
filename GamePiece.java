import java.awt.Point;

public class GamePiece {
    protected GameBoard gameBoard; // Reference to GameBoard for all pieces.

    private String[] imageFilenames = new String[1]; // Array of imageFilenames for game pieces. By default is size 1
    private Point[] piecePositions = new Point[1]; // Array of gamePiece positions. By default is size 1

    /**
     * Retrieves an array of imageFilenames for a game piece. By default array size is 1, can be overriden to 2 or 3 max.
     * @return imageFilenames string array
     */
    public String[] getImageFilenames(){
        return this.imageFilenames;
    }

    /**
     * Retrieves an array of points for a game piece. By default array size is 1, can be overriden to 2 or 3 max.
     * @return piecePositions point array
     */
    public Point[] getPositions(){
        return this.piecePositions;
    }
    /**
     * Places GamePiece at specific cordinates given a direction
     * @param x The X-Cord on gameBoard
     * @param y The Y-Cord on gameBoard
     * @param direction Direction enum for rotation of gamePiece
     */
    public void setPositions(int x, int y, Direction direction){
        gameBoard.placePiece(this, x, y, direction);
    }

    public Boolean isWalkable(){
        return false; // Default response, can be overriden
    }
}
