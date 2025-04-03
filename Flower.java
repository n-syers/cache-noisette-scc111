import java.awt.Point;

public class Flower extends GamePiece{
    private int size = 1; // size of the GamePiece on the board
    private Picture[] imageArray = new Picture[]{new Picture("assets\\icons\\Flower.png", 0)}; // Array of Pictures
    private Point[] piecePositions = new Point[]{new Point(0,0)}; // Array of gamePiece positions.
    private Point headPoint; // Position of the head on the board

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
    public void setPosition(int x, int y, Direction direction){
        headPoint = new Point(x, y);
        gameBoard.placePiece(this, x, y, direction, size);
    }

    @Override
    public Point[] getPiecePositions(){
        return piecePositions;
    }

    @Override  
    public Boolean isWalkable() {
        return false;
    }
}
