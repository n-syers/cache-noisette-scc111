import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * This class represents an instance of {@code GameBoard} in the game.
 * This class Manages the core logic between {@code GamePieces} and {@code JPanels}.
 * It manages the layout of Swing components and updates the JFrame based on game state.
 */
public class GameBoard{
    //references
    private JFrame cacheNoisetteJFrame; // Reference to main program JFrame
    protected LevelController levelController; // Reference to main LevelController

    // gameBoard variables
    private JPanel gameBoardPanel = new JPanel(); // Holds all grid button objects, representing the GameBoard state
    private JPanel centerPanel = new JPanel(); // Holds all game specific Swing objects displayed CENTER of cacheNoisetteJFrame
    private JButton[][] buttonBoard = new JButton[4][4]; // Holds the references to all JButtons on gameBoardPanel
    private GamePiece[][] gamePieceBoard = new GamePiece[4][4]; // Holds the reference to all Squirrel and Flower instances on gameBoardPanel
    private GamePiece[][] holesBoard = new GamePiece[4][4]; // Holes the reference to all Hole instances on gameBoardPanel
    private GamePiece selectedObject; // Holds a reference to the selected Squirrel instance
    /**
     * Constructs a new instance of {@code GameBoard}, initializing references to the {@code JFrame} 
     * and initialising the {@code Panels} and {@code Empty Board}.
     * @param cacheNoisetteJFrame The main program JFrame, used to place Swing components inside.
     */
    public GameBoard(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
        initialisePanel();
        initialiseEmptyBoards();
    }
    /**
     * Initialises all {@code JPanels} for the {@code GameBoard} and creates four instances of {@code MovementButton} for each direction.
     * These MovementButtons are added to a JPanel.
     */
    private void initialisePanel(){
        MovementButton northButton = new MovementButton(this, Direction.NORTH, MovementButton.Size.LARGE);
        MovementButton southButton = new MovementButton(this, Direction.SOUTH, MovementButton.Size.LARGE);
        MovementButton eastButton = new MovementButton(this, Direction.EAST, MovementButton.Size.SMALL);
        MovementButton westButton = new MovementButton(this, Direction.WEST, MovementButton.Size.SMALL);
        centerPanel.setLayout(new GridLayout(4,4,1,1));
        centerPanel.setPreferredSize(new Dimension(400,400));
        centerPanel.setBackground(Color.BLACK);

        gameBoardPanel.setLayout(new BorderLayout());
        gameBoardPanel.setPreferredSize(new Dimension(600,600));
        gameBoardPanel.setBackground(Color.BLACK);

        gameBoardPanel.add(northButton, BorderLayout.NORTH);
        gameBoardPanel.add(southButton, BorderLayout.SOUTH);
        gameBoardPanel.add(eastButton, BorderLayout.EAST);
        gameBoardPanel.add(westButton, BorderLayout.WEST);
        gameBoardPanel.add(centerPanel, BorderLayout.CENTER);
    }
    /**
     * Initialises the {@code centerPanel} with 4x4 grid of {@code JButtons}, each button is assigned an {@code ImageIcon}.
     * Initialises the {@code GameBoard} to empty state with only {@code Hole} and {@code Empty Button} instances.
     */
    public void initialiseEmptyBoards(){

        // Initialise Empty 2D arrays with Objects and JButtons
        for (int row = 0; row < buttonBoard.length; row++) {
            for (int col = 0; col < buttonBoard.length; col++) {
                buttonBoard[row][col] = new JButton(new Picture("./assets/icons/Empty.png", 0));
                buttonBoard[row][col].setPreferredSize(new Dimension(100, 100));
                buttonBoard[row][col].setBorder(null);
                int i = row;
                int j = col;
                buttonBoard[row][col].addActionListener(e -> processButtonPress(new Point(j, i)));
                centerPanel.add(buttonBoard[row][col]);
            }
        }
        try {
            System.out.println("Initialising Empty GameBoard");
            BufferedImage bufferedImage = FileManager.readBitmapAsBufferedImage("./assets/levels/blankWithHoles.bmp");

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
    /**
     * Clears the {@code centerPanel} of all Swing components and clears all {@code Array2D}.
     */
    public void clearGameBoard(){
        centerPanel.removeAll();
        buttonBoard = new JButton[4][4];
        gamePieceBoard = new GamePiece[4][4];
        holesBoard = new GamePiece[4][4];
    }
    /**
     * Places a {@code GamePiece} instance on the {@code GameBoard} as specified coordinates.
     * @param piece The GamePiece instance to place on the GameBoard.
     * @param x The integer X coordinate to place the GamePiece.
     * @param y The integer Y coordinate to place the GamePiece.
     * @param size The integer Size of the GamePiece.
     */
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
                buttonBoard[trueY][trueX].setIcon(piece.getPictures()[i]);
                System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
            }
        } else if (piece instanceof Flower) {
            gamePieceBoard[y][x] = piece;
            buttonBoard[y][x].setIcon(piece.getPictures()[0]);
            System.out.println("Placing GamePiece at: (" + x + ", " + y + ")");
        }    
        buttonBoard[y][x].revalidate();
        buttonBoard[y][x].repaint();
    }
    /**
     * Removes an instance of {@code GamePiece} from the {@code GameBoard}.
     * @param piece The GamePiece instance to remove.
     */
    public void removePiece(GamePiece piece){
        if (piece instanceof Squirrel) {
            Squirrel squirrel = (Squirrel) piece;
            Point headPoint = (Point) squirrel.getHeadPosition();
            Point[] piecePoints = squirrel.getPiecePositions();
            for (int i = 0; i < squirrel.getSize(); i++) {
                int x = (int) headPoint.getX() + (int) piecePoints[i].getX();
                int y = (int) headPoint.getY() + (int) piecePoints[i].getY();
                gamePieceBoard[y][x] = null;
                buttonBoard[y][x].setIcon(new Picture("./assets/icons/Empty.png", 0));
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
    }
    /**
     * Updates the image of a {@code JButton} at specified coordiantes with new {@code Picture} instance.
     * @param x The integer X coordinate of JButton.
     * @param y The integer Y coordinate of JButton.
     * @param picture The Picture instance to update JButton with.
     */
    public void updateImageAt(int x, int y, Picture picture){
        buttonBoard[y][x].setIcon(picture);
        refreshFrame();
    }
    /**
     * Determines whether an instance of {@code Squirrel} with a nut and {@code Hole} without a nut overlap.
     * If these {@code GamePiece} instances overlap and meet conditions, their respective dropNut() and placeNut()
     * methods are called and {@code LevelController} {@code checkWinConditions()} is called.
     * @param x The X coordinate on the GameBoard
     * @param y The Y coordinate on the GameBoard
     */
    public void checkNuts(int x, int y){
        GamePiece[] gamePieces = (GamePiece[]) getPiecesAt(x, y);
        Squirrel squirrel = (Squirrel) gamePieces[0];
        if (!(squirrel.isCarryingNut())) {
            System.out.println("An Error Occured: Squirrel Is Not Carrying A Nut");
            return;
        }
        if(!(gamePieces[1] instanceof Hole)) {
            System.out.println("An Error Occured: Squirrel Not Over Hole");
            return;
        }
        Hole hole = (Hole) gamePieces[1];
        if (hole.hasNut()) {
            System.out.println("An Error Occured: Hole Already Has Nut");
            return;
        }
        hole.placeNut();
        squirrel.dropNut();
        updateImageAt(x, y, squirrel.getPictures()[0]);
        System.out.println("Success: Placed Nut In Hole");
        levelController.checkWinConditions();
    }
    /**
     * Ensures that {@code gameBoardPanel} is added to {@code cacheNoisetteJFrame} and that the {@code JFrame} is {@code revalidate()} and {@code repaint()} with changes.
     */
    public void refreshFrame(){
        BorderLayout layout = (BorderLayout) cacheNoisetteJFrame.getLayout();
        if (layout.getLayoutComponent(BorderLayout.CENTER) != gameBoardPanel) {
            cacheNoisetteJFrame.add(gameBoardPanel, BorderLayout.CENTER);
        }
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }
    /**
     * Retrieves the {@code selectedGamePiece} attribute.
     * @return A {@code GamePiece} reference of {@code selectedGamePiece} 
     */
    public GamePiece getSelectedGamePiece(){
        return selectedObject;
    }
    /**
     * Retrieves a reference to {@code gamePieceBoard}
     * @return A {@code GamePiece[][]} reference of {@code gamePieceBoard} 
     */
    public GamePiece[][] getPiecesBoard(){
        return gamePieceBoard;
    }
    /**
     * Retrieves a {@code GamePiece} Array of size 2 with {@code GamePiece} instances from {@code gamePieceBoard} and {@code holesBoard} at specified coordinates.
     * @param x The X coordinate to get {@code GamePiece} instances from.
     * @param y The Y coordinate to get {@code GamePiece} instances from.
     * @return An array of size 2 of {@code GamePiece} instances.
     */
    public GamePiece[] getPiecesAt(int x, int y){
        GamePiece[] pieces = new GamePiece[]{gamePieceBoard[y][x], holesBoard[y][x]};
        return pieces;
    }
    /**
     * Privatly processes a {@code JButton} press from {@code buttonBoard} given it's {@code Point}.
     * @param point The {@code Point} associated with the {@code JButton} in {@code buttonBoard}
     */
    private void processButtonPress(Point point){
        GamePiece[] pieces = (GamePiece[]) getPiecesAt((int) point.getX(), (int) point.getY());
        if (pieces[0] instanceof Squirrel) {
            selectedObject = pieces[0];
        }
    }
    /**
     * Initalises {@code levelController} with a reference to an existing {@code LevelController}
     * @param levelController The existing {@code LevelController} to reference
     */
    public void setLevelController(LevelController levelController){
        this.levelController = levelController;
    }
}
