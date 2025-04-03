import java.awt.Point;

public class Empty extends GamePiece{
    private int size = 1; // size of the GamePiece on the board
    private Picture[] imageArray; // Array of Pictures
    private Point[] piecePositions; // Array of gamePiece positions.

    public Empty(int x, int y, Direction direction){
        piecePositions = new Point[size];
        piecePositions[0] = new Point(0,0);
        imageArray = new Picture[size];
        imageArray[0] = new Picture("Empty", direction.getValue(direction));
    }
}
