import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LevelController {    
    protected GameBoard gameBoard;
    private JPanel activeLevel = new JPanel();
    private int imageWidth = 15;
    private int imageHeight = 15;

    public LevelController(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * Loads the level for the game based on the number provided. Takes levelNumber, finds .bmp file, loads level. Initiates Game Mechanics.
     * @param levelNumber The number for the level to load. e.g. if 1, load level file "level1.bmp"
     */
    public void loadLevel(int levelNumber){
        try {
            System.out.println("Loading Level " + levelNumber);
            File file = new File("assets/levels/level"+levelNumber+".bmp");
            BufferedImage bufferedImage = ImageIO.read(file);

            // Check every 3x3 pixels for RGB colour values. 
            for(int row = 0; row < imageWidth; row+=4){
                for(int col = 0; col < imageHeight; col+=4){
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
                        rgbArray[0][1] = 0;
                        rgbArray[1][0] = bufferedImage.getRGB(col, 1+row);
                        rgbArray[1][1] = 90;
                        rgbArray[2][0] = bufferedImage.getRGB(2+col, 1+row);
                        rgbArray[2][1] = 270;
                        rgbArray[3][0] = bufferedImage.getRGB(1+col, 2+row);
                        rgbArray[3][1] = 180;
                        for (int i = 0; i < rgbArray.length; i++) {
                            red = (rgbArray[i][0] >> 16) & 0xFF;
                            green = (rgbArray[i][0] >> 8) & 0xFF;
                            blue = rgbArray[i][0] & 0xFF;
                            if (red == 255 && green == 127 && blue == 39) {
                                System.out.println("Red Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                            } else if (red == 195 && green == 195 && blue == 195) {
                                System.out.println("Grey Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                            } else if (red == 239 && green == 228 && blue == 176) {
                               System.out.println("Brown Squirrel Head Space Detected with rotation " + rgbArray[i][1]);
                            } else if (red == 0 && green == 0 && blue == 0) {
                                System.out.println("Black Squirrel Head Detected with rotation " + rgbArray[i][1]);
                            }
                        }
                    } else if (red == 237 && green == 28 && blue == 36) {
                        System.out.println("Flower Space Detected");
                    } else if (red == 34 && green == 177 && blue == 76) {
                        System.out.println("Duel Flower Space Detected");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
