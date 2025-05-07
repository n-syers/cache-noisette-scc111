import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

/**
 * This class represents an instance of {@code Cache Noisette Game} program.
 * This class holds the {@code main()} method for the whole program.
 */
public class Driver {
    private static JFrame cacheNoisetteJFrame = new JFrame("Cache Noisette Game"); // Initialise a JFrame with Title accessible via all classes.
    /**
     * The main entry point of the Cache Noisette Game program.
     * Initializes and starts the game with relevant classes.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Dimension windowSize = new Dimension(400, 600); // Size of Window
        GameBoard gameBoard = new GameBoard(cacheNoisetteJFrame);
        MenuController menuController = new MenuController(cacheNoisetteJFrame);
        LevelController levelController = new LevelController(gameBoard);

        //Set circular dependencies
        menuController.setLevelController(levelController);
        levelController.setMenuController(menuController);

        //initialise the JFrame to hold all game content
        cacheNoisetteJFrame.setVisible(true);
        cacheNoisetteJFrame.setSize(windowSize);
        cacheNoisetteJFrame.setPreferredSize(windowSize);
        cacheNoisetteJFrame.setResizable(false);
        cacheNoisetteJFrame.setLocationRelativeTo(null);
        cacheNoisetteJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cacheNoisetteJFrame.setBackground(Color.BLACK);
        cacheNoisetteJFrame.setLayout(new BorderLayout());

        // Create and display initial menus and title
        menuController.createTitleLabel("Cache Noisette Game");
        menuController.createButtonMenu();
        menuController.createMenuBar();
    }

}
