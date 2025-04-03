import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class LevelController {    
    private GameBoard gameBoard;
    private String activeLevelPath;

    public LevelController(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * Loads the level for the game based on the number provided. Takes levelNumber, finds .bmp file, loads level. Initiates Game Mechanics.
     * @param levelNumber The number for the level to load. e.g. if 1, load level file "level1.bmp"
     */
    public void loadLevel(int levelNumber){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        gameBoard.renderBoard();
        try {
            System.out.println("Loading Level " + levelNumber);
            File file = new File("assets/levels/level"+levelNumber+".bmp");
            activeLevelPath = file.getPath();
            BufferedImage bufferedImage = ImageIO.read(file);
            loadData(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevel) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
    }

    /**
     * Load a custom bmp level file
     * @param levelFile
     */
    public void loadLevelFromFilePath(String levelFilePath){
        gameBoard.clearGameBoard();
        gameBoard.initialiseEmptyBoards();
        gameBoard.renderBoard();
        try {
            System.out.println("Loading Custom Level...");
            File file = new File(levelFilePath);
            BufferedImage bufferedImage = ImageIO.read(file);
            loadData(bufferedImage);
        } catch (Exception e) {
            System.err.println("(LEVELCONTROLLER::loadLevelFromFilePath) An error occurred: " + e.getMessage());
        }
        gameBoard.refreshFrame();
    }
    
    /**
     * This function loads data from a buffered image and initiates the level creation process
     * @param bufferedImage The image that is loaded
     */
    private void loadData(BufferedImage bufferedImage){
        try {
            System.out.println("Processing Level Data...");

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
                                gameBoard.placePiece(new Squirrel(Colour.RED, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2), col/4, row/4, Direction.getDirection(rgbArray[i][1]), 2);
                            } else if (red == 195 && green == 195 && blue == 195) {
                                System.out.println("Grey Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Colour.GREY, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 2), col/4, row/4, Direction.getDirection(rgbArray[i][1]), 2);
                            } else if (red == 239 && green == 228 && blue == 176) {
                               System.out.println("Brown Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                               gameBoard.placePiece(new Squirrel(Colour.BROWN, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3), col/4, row/4, Direction.getDirection(rgbArray[i][1]), 3);
                            } else if (red == 0 && green == 0 && blue == 0) {
                                System.out.println("Black Squirrel Head Detected with rotation " + rgbArray[i][1]);
                                gameBoard.placePiece(new Squirrel(Colour.BLACK, Direction.getDirection(rgbArray[i][1]), col/4, row/4, 3), col/4, row/4, Direction.getDirection(rgbArray[i][1]), 3);
                            }
                        }
                    } else if (red == 237 && green == 28 && blue == 36) {
                        System.out.println("Flower Space Detected");
                        gameBoard.placePiece(new Flower(), col/4, row/4, Direction.NORTH, 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * This function calls loadLevel with the current levelNumber. Restarting the level.
     */
    public void restartLevel(){
        loadLevelFromFilePath(activeLevelPath);
    }
}
