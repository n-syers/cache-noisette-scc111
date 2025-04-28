import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class manages the core logic between the GameBoard and MenuController.
 * Responsible for handling user input and updating the game state. 
 * This class also handles the processing of BufferedImage data to load levels.
 */
public class LevelController {    
    private GameBoard gameBoard; // Reference to GameBoard Object to access methods
    private MenuController menuController; // Reference to MenuController Object to access methods
    private String activeLevelPath; // Current file path to active level
    private int numberSquirrels; // Number of squirrels active
    private int nutsCollected; // Number of nuts collected by user

    /**
     * Constructs a new instance of {@code LevelController}, initializing references to the GameBoard 
     * and MenuController, sets default values for variables, and parses reference of
     * this LevelController instance to gameBoard.
     * @param gameBoard      The GameBoard instance that manages the game state and UI components.
     */
    public LevelController(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.numberSquirrels = 0;
        this.nutsCollected = 0;
        gameBoard.setLevelController(this);
    }

    /**
     * Load a game level from the specified level number. 
     * Takes level number and locates the corresponding {@code .bmp file}, then calls {@code loadData()} to process
     * the level data and initiate Game Mechanics.
     * @param levelNumber Integer that corresponds to the level bmp file.
     */
    public void loadLevel(int levelNumber){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        try {
            System.out.println("Loading Level " + levelNumber);
            File file = new File("./assets/levels/level"+levelNumber+".bmp");
            activeLevelPath = file.getPath();
            BufferedImage bufferedImage = FileManager.readBitmapAsBufferedImage(activeLevelPath);
            loadDataFromBufferedImage(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevel) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
    }

    /**
     * Load a game level from the specified file path. 
     * Takes file path and locates the corresponding {@code .bmp file}, then calls {@code loadData()} to process
     * the level data and initiate Game Mechanics.
     * @param levelFilePath String that holds the file path of the bmp file.
     */
    public void loadLevelFromFilePath(String levelFilePath){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        try {
            System.out.println("Loading Custom Level...");
            BufferedImage bufferedImage = FileManager.readBitmapAsBufferedImage(levelFilePath);
            loadDataFromBufferedImage(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevelFromFilePath) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
    }
    /**
     * Allows the user to select a bmp file and then calls {@code loadData}
     * to process the files data
     */
    public void loadCustomLevel(){
        System.out.println("Loading Custom File...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Valid Level File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Bitmap Images", "bmp"));
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                loadLevelFromFilePath(selectedFile.getAbsolutePath());
        } else {
                System.out.println("No file selected");
        }
    }
    
    /**
     * Loads data from a {@code BufferedImage} and initiates the level 
     * creation process based on the image content.
     * @param bufferedImage The {@code BufferedImage} to be processed.
     */
    private void loadDataFromBufferedImage(BufferedImage bufferedImage){
        try {
            System.out.println("Processing Level Data...");
            numberSquirrels = 0;
            nutsCollected = 0;
            // Check every 3x3 pixels for RGB colour values. 
            for(int row = 0; row < 15; row+=4){
                for(int col = 0; col < 15; col+=4){
                    // Get RGB value from center of each 3x3 in image.
                    int rgb = bufferedImage.getRGB(1+col, 1+row);
                    // Extract red, green, and blue components from the RGB value
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    // Determin if the piece is a Squirrel or Flower
                    if (red == 255 && green == 201 && blue == 14) {
                        System.out.println("Squirrel Head Space Detected");
                        int[][] rgbArray = new int[4][2];
                        rgbArray[0][0] = bufferedImage.getRGB(1+col, row);
                        rgbArray[0][1] = 180;
                        rgbArray[1][0] = bufferedImage.getRGB(col, 1+row);
                        rgbArray[1][1] = 90;
                        rgbArray[2][0] = bufferedImage.getRGB(2+col, 1+row);
                        rgbArray[2][1] = 270;
                        rgbArray[3][0] = bufferedImage.getRGB(1+col, 2+row);
                        rgbArray[3][1] = 0;
                        for (int i = 0; i < rgbArray.length; i++) {
                            red = (rgbArray[i][0] >> 16) & 0xFF;
                            green = (rgbArray[i][0] >> 8) & 0xFF;
                            blue = rgbArray[i][0] & 0xFF;
                            if (red == 255 && green == 127 && blue == 39) {
                                System.out.println("Red Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Squirrel.Colour.RED, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2, gameBoard), col/4, row/4, 2);
                            } else if (red == 195 && green == 195 && blue == 195) {
                                System.out.println("Grey Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Squirrel.Colour.GREY, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2, gameBoard), col/4, row/4, 2);
                            } else if (red == 239 && green == 228 && blue == 176) {
                               System.out.println("Brown Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                               gameBoard.placePiece(new Squirrel(Squirrel.Colour.BROWN, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3, gameBoard), col/4, row/4, 3);
                            } else if (red == 0 && green == 0 && blue == 0) {
                                System.out.println("Black Squirrel Head Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Squirrel.Colour.BLACK, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3, gameBoard), col/4, row/4, 3);
                            }
                        }
                        numberSquirrels++;
                    } else if (red == 237 && green == 28 && blue == 36) {
                        System.out.println("Flower Space Detected");
                        gameBoard.placePiece(new Flower(), col/4, row/4, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadData) An error occurred: " + e.getMessage());
        }
        System.out.println("Processing Complete");
    }
    /**
     * Checks whether the win conditions have been met. If so, generates a popup {@code JFrame} from {@code menuController}.
     */
    public void checkWinConditions(){
        nutsCollected++;
        System.out.println("Nut collected. LevelController count: " + nutsCollected);
        if (nutsCollected == numberSquirrels) {
            System.out.println("Win Condition Met");
            menuController.createPopUp("You Win!", "CONGRATULATIONS\nYou have beaten the level!\nAccess more levels via the menu at the top.");
            
        }
    }
    /**
     * Restarts the current level by calling {@code loadLevel()} with the {@code activeLevelPath} to reload the level.
     */
    public void restartLevel(){
        loadLevelFromFilePath(activeLevelPath);
    }
    /**
     * Sets the reference to a {@code MenuController} instance, allowing
     * the {@code LevelController} to communicate with the {@code MenuController}.
     * @param menuController The MenuController instance to be referenced
     */
    public void setMenuController(MenuController menuController){
        this.menuController = menuController;
    }
}
