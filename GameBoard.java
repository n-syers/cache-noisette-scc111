import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameBoard{
    protected JFrame cacheNoisetteJFrame;

    private JPanel gameBoardPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JButton[][] buttonBoard = new JButton[4][4];
    private GamePiece[][] gamePieceBoard = new GamePiece[4][4];
    private GamePiece[][] holesBoard = new GamePiece[4][4];
    private GamePiece selectedObject;

    public GameBoard(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
        initialisePanel();
        initialiseEmptyBoards();
    }
    private void initialisePanel(){
        MovementButton northButton = new MovementButton(this, Direction.NORTH, MovementButton.Size.LARGE);
        MovementButton southButton = new MovementButton(this, Direction.SOUTH, MovementButton.Size.LARGE);
        MovementButton eastButton = new MovementButton(this, Direction.EAST, MovementButton.Size.SMALL);
        MovementButton westButton = new MovementButton(this, Direction.WEST, MovementButton.Size.SMALL);
        centerPanel.setLayout(new GridLayout(4,4));
        centerPanel.setMaximumSize(new Dimension(400,400));
        centerPanel.setMinimumSize(new Dimension(400,400));
        centerPanel.setPreferredSize(new Dimension(400,400));

        gameBoardPanel.setLayout(new BorderLayout());
        gameBoardPanel.setMaximumSize(new Dimension(600,600));
        gameBoardPanel.setMinimumSize(new Dimension(600,600));
        gameBoardPanel.setPreferredSize(new Dimension(600,600));
        gameBoardPanel.setBackground(Color.BLACK);

        gameBoardPanel.add(northButton, BorderLayout.NORTH);
        gameBoardPanel.add(southButton, BorderLayout.SOUTH);
        gameBoardPanel.add(eastButton, BorderLayout.EAST);
        gameBoardPanel.add(westButton, BorderLayout.WEST);
        gameBoardPanel.add(centerPanel, BorderLayout.CENTER);
    }

    public void initialiseEmptyBoards(){

        // Initialise Empty 2D arrays with Objects and JButtons
        for (int row = 0; row < buttonBoard.length; row++) {
            for (int col = 0; col < buttonBoard.length; col++) {
                buttonBoard[row][col] = new JButton(new Picture("assets\\icons\\Empty.png", 0));
                int i = row;
                int j = col;
                buttonBoard[row][col].addActionListener(e -> processButtonPress(new Point(j, i)));
                centerPanel.add(buttonBoard[row][col]);
            }
        }
        try {
            System.out.println("Initialising Empty GameBoard");
            File file = new File("assets\\levels\\blankWithHoles.bmp");
            BufferedImage bufferedImage = ImageIO.read(file);

            // Check every 3x3 pixels for RGB colour values. 
            for(int row = 0; row < 15; row+=4){
                for(int col = 0; col < 15; col+=4){
                    // Get RGB value from center of each 3x3 in image.
                    int rgb = bufferedImage.getRGB(1+col, 1+row);
                    // Extract red, green, and blue components from the RGB value
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    // Determin if to place a hole at cord.
                    if (red == 255 && green == 255 && blue == 255) {
                        System.out.println("Hole Space Detected");
                        Hole hole = new Hole();
                        placePiece(hole, col/4, row/4, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void placePiece(GamePiece piece, int x, int y, int size){
        if (piece instanceof Hole) {
            holesBoard[y][x] = piece;
            buttonBoard[y][x].setIcon(piece.getPictures()[0]);
            System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
        } else if (piece instanceof Squirrel) {
            for (int i = 0; i < size; i++) {
                int trueX = x + (int) piece.getPiecePositions()[i].getX();
                int trueY = y + (int) piece.getPiecePositions()[i].getY();
                gamePieceBoard[trueY][trueX] = piece;
                buttonBoard[trueY][trueX].setIcon(piece.getPictures()[1+i]);
                System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
            }
        } else if (piece instanceof Flower) {
            gamePieceBoard[y][x] = piece;
            buttonBoard[y][x].setIcon(piece.getPictures()[0]);
            System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
        }    
        buttonBoard[y][x].revalidate();
        buttonBoard[y][x].repaint();
        refreshFrame();
    }

    public GamePiece[] getPiecesAt(int x, int y){
        GamePiece[] pieces = new GamePiece[]{gamePieceBoard[y][x], holesBoard[y][x]};
        return pieces;
    }

    public void updateImageAt(int x, int y, Picture picture){

    }

    public void movePiece(GamePiece piece, Direction direction){
        if(!(piece instanceof Squirrel)){
            System.out.println("An Error Occured: No Squirrel Selected");
            return;
        }
        Squirrel squirrel = (Squirrel) piece;
        Point headPoint = (Point) squirrel.getHeadPosition();
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

        if (canMove(squirrel, newX, newY) == true) {
            removePiece(piece);
            squirrel.setPosition(newX, newY);
            placePiece(squirrel, newX, newY, squirrel.getSize());
        }
    }
    public Boolean canMove(Squirrel squirrel, int x, int y){
        Boolean isLegal = true;
        Point[] piecePoints = squirrel.getPiecePositions();
        for (int i = 0; i < piecePoints.length; i++) {
            int newX = x + (int) piecePoints[i].getX();
            int newY = y + (int) piecePoints[i].getY();
            if (newX < 0 || newX >= 4 || newY < 0 || newY >= 4) {
                System.out.println("Move out of bounds!");
                isLegal = false;
                break;
            }
    
            // Ensure the new position is not occupied by another game piece
            if (gamePieceBoard[newY][newX] != null) {
                if (gamePieceBoard[newY][newX] instanceof Flower || gamePieceBoard[newY][newX] instanceof Squirrel && gamePieceBoard[newY][newX] != squirrel) {
                    System.out.println("Cannot move into an occupied space!");
                    isLegal = false;
                    break;
                }
            }
        }
        return isLegal;
    }
    public void removePiece(GamePiece piece){
        if (piece instanceof Squirrel) {
            Squirrel squirrel = (Squirrel) piece;
            Point headPoint = (Point) squirrel.getHeadPosition();
            Point[] piecePoints = squirrel.getPiecePositions();
            for (int i = 0; i < squirrel.getSize(); i++) {
                int x = (int) headPoint.getX() + (int) piecePoints[i].getX();
                int y = (int) headPoint.getY() + (int) piecePoints[i].getY();
                gamePieceBoard[y][x] = null;
                buttonBoard[y][x].setIcon(new Picture("assets\\icons\\Empty.png", 0));
                GamePiece boardPiece = (GamePiece) getPiecesAt(x, y)[1];
                if(boardPiece instanceof Hole){
                    Hole hole = (Hole) boardPiece;
                    if(hole.hasNut()){
                        buttonBoard[y][x].setIcon(hole.getPictures()[1]);
                    } else {
                        buttonBoard[y][x].setIcon(hole.getPictures()[0]);
                    }
                }
                System.out.println("Removing GamePiece at: (" + (int) headPoint.getX() + ", " + (int) headPoint.getY() + ")");
                buttonBoard[y][x].revalidate();
                buttonBoard[y][x].repaint();
            }
        }
        refreshFrame();
    }
    public void clearGameBoard(){
        centerPanel.removeAll();
        buttonBoard = new JButton[4][4];
        gamePieceBoard = new GamePiece[4][4];
        holesBoard = new GamePiece[4][4];

    }
    public void renderBoard(){
        cacheNoisetteJFrame.add(gameBoardPanel, BorderLayout.CENTER);
        refreshFrame();
    }
    public void refreshFrame(){
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }
    
    private void processButtonPress(Point point){
        GamePiece[] pieces = (GamePiece[]) getPiecesAt((int) point.getX(), (int) point.getY());
        if (pieces[0] instanceof Squirrel) {
            selectedObject = pieces[0];
        }
    }

    public GamePiece getSelectedGamePiece(){
        return selectedObject;
    }
}
