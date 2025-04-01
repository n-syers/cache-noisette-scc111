import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LevelController {
    private JPanel activeLevel = new JPanel();
    private int imageWidth = 15;
    private int imageHeight = 15;
    
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
                    } else if (red == 237 && green == 28 && blue == 36) {
                        System.out.println("Flower Space Detected");
                    } else if (red == 34 && green == 177 && blue == 76) {
                        System.out.println("Duel Flower Space Detected");
                    } else if (red == 255 && green == 127 && blue == 39) {
                        System.out.println("Red Squirrel Body Space Detected");
                    } else if (red == 195 && green == 195 && blue == 195) {
                        System.out.println("Grey Squirrel Body Space Detected");
                    } else if (red == 239 && green == 228 && blue == 176) {
                       System.out.println("Brown Squirrel Body Space Detected");
                    } else if (red == 0 && green == 0 && blue == 0) {
                        System.out.println("Black Squirrel Body Detected");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
