import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameBoard {
    protected JFrame cacheNoisetteJFrame;

    private JPanel gameBoardPanel = new JPanel();
    private JButton[][] buttonBoard = new JButton[4][4];
    private Object[][] gamePieceBoard = new Object[4][4];
    private Object[][] holesBoard = new Object[4][4];

    public GameBoard(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
        initialisePanel();
        initialiseEmptyBoards();
    }
    private void initialisePanel(){
        gameBoardPanel.setLayout(new GridLayout(4,4));
        gameBoardPanel.setMaximumSize(new Dimension(400,400));
        gameBoardPanel.setMinimumSize(new Dimension(400,400));
        gameBoardPanel.setPreferredSize(new Dimension(400,400));
        gameBoardPanel.setBackground(Color.BLACK);
    }

    public void initialiseEmptyBoards(){

        // Initialise Empty 2D arrays with Objects and JButtons
        for (int row = 0; row < buttonBoard.length; row++) {
            for (int col = 0; col < buttonBoard.length; col++) {
                buttonBoard[row][col] = new JButton(new Picture("assets\\icons\\Empty.png", 0));
                gameBoardPanel.add(buttonBoard[row][col]);
                gamePieceBoard[row][col] = new Object();
                holesBoard[row][col] = new Object();
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
                        placePiece(hole, col/4, row/4, Direction.NORTH, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void placePiece(GamePiece piece, int x, int y, Direction direction, int size){
        if (piece instanceof Hole) {
            holesBoard[y][x] = piece;
            buttonBoard[y][x].setIcon(piece.getPictures()[0]);
            System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
            buttonBoard[y][x].revalidate();
            buttonBoard[y][x].repaint();
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
            buttonBoard[y][x].revalidate();
            buttonBoard[y][x].repaint();
        }    
        refreshFrame();
    }

    public Object getPiecesAt(int x, int y){
        Object[] pieces = new Object[]{gamePieceBoard[y][x], holesBoard[y][x]};
        return pieces;
    }

    public void updateImageAt(GamePiece piece, int x, int y, Picture picture){

    }

    public void movePiece(GamePiece piece, Direction direction){

    }

    public void clearGameBoard(){
        
    }
    public void renderBoard(){
        cacheNoisetteJFrame.add(gameBoardPanel, BorderLayout.CENTER);
        refreshFrame();
    }
    public void refreshFrame(){
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }
}
