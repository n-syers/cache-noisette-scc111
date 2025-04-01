import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
        cacheNoisetteJFrame.setMinimumSize(windowSize);
        cacheNoisetteJFrame.setMaximumSize(windowSize);
        cacheNoisetteJFrame.setPreferredSize(windowSize);
        cacheNoisetteJFrame.setLocationRelativeTo(null);
        cacheNoisetteJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cacheNoisetteJFrame.setBackground(Color.BLACK);
        cacheNoisetteJFrame.setLayout(new BorderLayout());

        // In-JFrame Title creation and properties
        JLabel titleLable = new JLabel();
        titleLable.setText("Cache Noisette Game");
        titleLable.setFont(new java.awt.Font("Segoe UI Black", 1, 18));
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);
        titleLable.setMaximumSize(new java.awt.Dimension(200, 75));
        titleLable.setMinimumSize(new java.awt.Dimension(200, 75));
        titleLable.setPreferredSize(new java.awt.Dimension(200, 75));
        cacheNoisetteJFrame.add(titleLable, BorderLayout.NORTH);

        // Add the menus to the JFrame
        cacheNoisetteJFrame.add(menuController.getButtonMenu(), BorderLayout.CENTER);
        cacheNoisetteJFrame.setJMenuBar(menuController.getMenuBar());
    }
    

}
