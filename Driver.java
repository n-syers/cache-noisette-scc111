import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

/**
 * This class holds the main() for all other files but also initialises a JFrame for all components to be added to.
 */
public class Driver {
    private static JFrame cacheNoisetteJFrame = new JFrame("Cache Noisette Game"); // Initialise a JFrame with Title accessible via all classes.
    private static MenuController menuController = new MenuController(); // Used to control the menus in the program.

    public static void main(String[] args) {
        Dimension windowSize = new Dimension(900, 900); // Size of Window
        
        //initialise the JFrame to hold all game content
        cacheNoisetteJFrame.setVisible(true);
        cacheNoisetteJFrame.setSize(windowSize);
        cacheNoisetteJFrame.setPreferredSize(windowSize);
        cacheNoisetteJFrame.setResizable(false);
        cacheNoisetteJFrame.setLocationRelativeTo(null);
        cacheNoisetteJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cacheNoisetteJFrame.setBackground(Color.BLACK);
        cacheNoisetteJFrame.setLayout(new BorderLayout());

        // Create the title label with properties.
        cacheNoisetteJFrame.add(menuController.createTitleLabel("Cache Noisette Game"), BorderLayout.NORTH);

        // Add the menu and menuBar to the JFrame
        cacheNoisetteJFrame.add(menuController.getButtonMenu(), BorderLayout.CENTER);
        cacheNoisetteJFrame.setJMenuBar(menuController.getMenuBar());
    }

}
