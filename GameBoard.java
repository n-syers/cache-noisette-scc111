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

    public GameBoard(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
    }

    public void initialiseEmptyBoard(){
        try {
            System.out.println("Initialising Empty GameBoard");
            File file = new File("assets/levels/blankWithHoles.bmp");
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
                    // Determin what piece is what through if statements.
                    if (red == 185 && green == 122 && blue == 87) {
                        System.out.println("Empty Space Detected");
                    } else if (red == 255 && green == 255 && blue == 255) {
                        System.out.println("Hole Space Detected");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void placePiece(GamePiece piece, int x, int y, Direction direction, int size){

    }

    public GamePiece getPieceAt(int x, int y){
        GamePiece piece = new GamePiece();
        return piece;
    }

    public void movePiece(GamePiece piece, Direction direction){

    }

    public void clearGameBoard(){
        
    }
}
