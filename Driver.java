import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        Dimension windowSize = new Dimension(900, 900); // Size of Window
        
        //initialise the JFrame to hold all game content
        JFrame cacheNoisetteJFrame = new JFrame("Cache Noisette Game"); // Initialise a JFrame with Title
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
        JLabel titleLable = new JLabel(); // Title label for the JFrame
        titleLable.setText("Cache Noisette Game");
        titleLable.setFont(new java.awt.Font("Segoe UI Black", 1, 18));
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);
        titleLable.setMaximumSize(new java.awt.Dimension(200, 75));
        titleLable.setMinimumSize(new java.awt.Dimension(200, 75));
        titleLable.setPreferredSize(new java.awt.Dimension(200, 75));

        // Add components to JFrame
        cacheNoisetteJFrame.getContentPane().add(titleLable, BorderLayout.NORTH);

    }

}
