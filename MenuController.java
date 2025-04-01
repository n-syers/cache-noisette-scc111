import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class MenuController implements ActionListener{
    private JPanel buttonMenu = new JPanel(); // Holds all JSwing Objects for Main Menu
    private JPanel buttonGroup = new JPanel(); // Holds both main menu buttons.
    private JButton playButton = new JButton(); // Initiates the loading of Level 1
    private JButton quitButton = new JButton(); // Exits Program with Status 0
    private JMenuBar gameMenuBar = new JMenuBar(); // Menu bar for during gameplay
    private JMenu levelSelectorMenu = new JMenu(); // Holds all levels that can be selected
    private JMenuItem customLevelMenuItem = new JMenuItem(); // Button for Custom Level Loading

    /**
     * Constructor for MenuController.
     * Populates the buttonMenu and gameMenuBar with buttons and actionListeners.
     */
    MenuController(){
        // Set initialPlayMenu layout to GridBagLayout
        buttonMenu.setLayout(new GridBagLayout());

        // Set buttonGroup Properties
        buttonGroup.setMaximumSize(new Dimension(400, 300));
        buttonGroup.setMinimumSize(new Dimension(400, 150));
        buttonGroup.setPreferredSize(new Dimension(400, 150));
        buttonGroup.setLayout(new GridLayout(2, 1, 0, 20));

        // Set playButton Properties
        playButton.setText("Click to Play");
        playButton.setMaximumSize(null);
        playButton.setMinimumSize(null);
        playButton.setPreferredSize(new Dimension(200, 75));
        playButton.addActionListener(this);
        buttonGroup.add(playButton);

        // Set quitButton Properties
        quitButton.setText("Quit Game");
        quitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quitButton.setMaximumSize(null);
        quitButton.setMinimumSize(null);
        quitButton.setPreferredSize(new Dimension(200, 75));
        quitButton.addActionListener(this);
        buttonGroup.add(quitButton);

        // Add buttonGroup to initialPlayMenu
        buttonMenu.add(buttonGroup, new GridBagConstraints());

        /* Create the Menu Bar */

        // Set levelSelectorMenu Properties
        levelSelectorMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        levelSelectorMenu.setText("Levels");

        // Set customLevelMenuItem Properties and Add to levelSelectorMenu
        customLevelMenuItem.setText("Load Custom Level");
        customLevelMenuItem.addActionListener(this);
        levelSelectorMenu.add(customLevelMenuItem);

        /*
         * For each file in directoryPath that matches pattern "level*.bmp"
         * Get fileName and create String "Level ##" for menuItem
         * Add menuItem to menu with actionListener.
         * Try and Catch for errors.
         */
        Path directoryPath = Paths.get("assets/levels/");
        String menuItemNameString = "";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, "level*.bmp")) {
            for (Path entry : stream) {
                System.out.println("Found Level File: " + entry.getFileName());
                menuItemNameString = entry.getFileName().toString();
                menuItemNameString = menuItemNameString.replace("level", "Level ").replace(".bmp", "");
                JMenuItem levelMenuItem = new JMenuItem();
                levelMenuItem.setText(menuItemNameString);
                levelMenuItem.addActionListener(this); //Not optimal as event listener will need to check button text.
                levelSelectorMenu.add(levelMenuItem);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        gameMenuBar.add(levelSelectorMenu);
    }
    
    /**
     * This function creates a JLabel with the text string its provided with preset formats.
     * @param titleString The text inside of the label.
     * @return The JLabel after formatting
     */
    public JLabel createTitleLabel(String titleString){
        JLabel titleLabel = new JLabel(titleString);
        titleLabel.setFont(new Font("Segoe UI Black", 1, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setMaximumSize(new Dimension(200, 75));
        titleLabel.setMinimumSize(new Dimension(200, 75));
        titleLabel.setPreferredSize(new Dimension(200, 75));
        return titleLabel;
    }
    
    /**
     * Returns the JPanel for the button menu.
     * @return gameButtonMenu
     */
    public JPanel getButtonMenu(){
        return this.buttonMenu;
    }

    /**
     * Returns the JMenuBar for the main menu bar.
     * @return gameMenuBar
     */
    public JMenuBar getMenuBar(){
        return this.gameMenuBar;
    }
    
    /**
     * This function is called when a button is pressed in one of the menus.
     * The button will correspond to an action inside of this function.
     */
    public void actionPerformed(ActionEvent e){

        // load the first level of the game.
        if(e.getSource() == playButton){
             System.out.println("Play Button Pressed");
             buttonMenu.setVisible(false);
        };

        // exit the game with status 0
        if(e.getSource() == quitButton){
             System.out.println("Quit Button Pressed, Goodbye!");
             System.exit(0);
        };
    }
}
