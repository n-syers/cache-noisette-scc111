import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

/**
 * This class holds the main() for all other files but also initialises a JFrame for all components to be added to.
 */
public class Driver {
    private static JFrame cacheNoisetteJFrame = new JFrame("Cache Noisette Game"); // Initialise a JFrame with Title accessible via all classes.
    public static void main(String[] args) {
        Dimension windowSize = new Dimension(700, 800); // Size of Window
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
