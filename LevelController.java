import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LevelController {    
    private GameBoard gameBoard; // Reference to GameBoard Object to access methods
    private MenuController menuController; // Reference to MenuController Object to access methods
    private String activeLevelPath; // Current file path to active level
    private int currentLevelNum = 1; // Current level number. Set to 1 By default.
    private int numberSquirrels = 0; // Number of squirrels active. 
    private int nutsCollected = 0; // Number of nuts collected by user. Set to 0 by default.

    /**
     * Constructor for LevelController. Creates a reference to gameBoard to allow the class to control the same gameBoard.
     * @param gameBoard
     */
    public LevelController(GameBoard gameBoard, MenuController menuController){
        this.gameBoard = gameBoard;
        this.menuController = menuController;
        gameBoard.setLevelController(this);
    }

    /**
     * Loads the level for the game based on the number provided. Takes levelNumber, finds .bmp file, loads level. Initiates Game Mechanics.
     * @param levelNumber The number for the level to load. e.g. if 1, load level file "level1.bmp"
     */
    public void loadLevel(int levelNumber){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        gameBoard.refreshFrame();
        try {
            System.out.println("Loading Level " + levelNumber);
            File file = new File("assets/levels/level"+levelNumber+".bmp");
            activeLevelPath = file.getPath();
            BufferedImage bufferedImage = getBufferedImageFromPath(activeLevelPath);
            loadData(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevel) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
        currentLevelNum = levelNumber;
    }

    /**
     * Load the leve for the game via the provided file path. Take path and finds .bmp file, calls loadData() to process level data. Initiates Game Mechanics.
     * @param levelFilePath String that holds the file path of the bmp file.
     */
    public void loadLevelFromFilePath(String levelFilePath){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        gameBoard.refreshFrame();
        try {
            System.out.println("Loading Custom Level...");
            BufferedImage bufferedImage = getBufferedImageFromPath(levelFilePath);
            loadData(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevelFromFilePath) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
    }
    /**
     * This function allows the user to enter a bmp file and then calls loadData to process the data
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
     * The function takes a directory path as a string and returns a BufferedImage using RandomAccessFile data streams. Byte data is read row-by-row and converted to RGB values and set on an empty bufferedImage.
     * @param levelFilePath String of the file path.
     * @return The bufferedImage after the image is created.
     */
    private BufferedImage getBufferedImageFromPath(String levelFilePath){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(levelFilePath, "r");
            randomAccessFile.skipBytes(54);
            BufferedImage bufferedImage = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            int rowLength = 15 * 3;
            int paddedRowLength = (rowLength + 3) & ~3;
            byte[] pixelData = new byte[paddedRowLength]; // 15x15 pixel image with 3 bytes per pixel rounded to the next multiple of 4
            for (int y = 15 - 1; y >= 0; y--) {
                randomAccessFile.readFully(pixelData); // Read one row of pixel data
                for (int x = 0; x < 15; x++) {
                    int blue = pixelData[x * 3] & 0xFF;
                    int green = pixelData[x * 3 + 1] & 0xFF;
                    int red = pixelData[x * 3 + 2] & 0xFF;
                    int rgb = (red << 16) | (green << 8) | blue;
                    bufferedImage.setRGB(x, y, rgb);
                }
            }
            randomAccessFile.close();
            return bufferedImage;
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::getBufferedImageFromPath) An error occurred: " + e.getMessage());
            return null;
        }
    }
    /**
     * This function loads data from a buffered image and initiates the level creation process
     * @param bufferedImage The image that is loaded
     */
    private void loadData(BufferedImage bufferedImage){
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
                    // Determin what piece is what through if statements.
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
                                gameBoard.placePiece(new Squirrel(Colour.RED, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2, gameBoard), col/4, row/4, 2);
                            } else if (red == 195 && green == 195 && blue == 195) {
                                System.out.println("Grey Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Colour.GREY, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2, gameBoard), col/4, row/4, 2);
                            } else if (red == 239 && green == 228 && blue == 176) {
                               System.out.println("Brown Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                               gameBoard.placePiece(new Squirrel(Colour.BROWN, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3, gameBoard), col/4, row/4, 3);
                            } else if (red == 0 && green == 0 && blue == 0) {
                                System.out.println("Black Squirrel Head Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Colour.BLACK, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3, gameBoard), col/4, row/4, 3);
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
    public void checkWinConditions(){
        nutsCollected++;
        System.out.println("Nut collected. LevelController count: " + nutsCollected);
        if (nutsCollected == numberSquirrels) {
            System.out.println("Win Condition Met");
            menuController.createPopUp("You Win!", "CONGRATULATIONS\nYou have beaten the level!\nAccess more levels via the menu at the top.");
            
        }
    }
    /**
     * This function calls loadLevel with the current levelNumber. Restarting the level.
     */
    public void restartLevel(){
        loadLevelFromFilePath(activeLevelPath);
    }
}
