import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * The MenuController class is responsible for managing and controlling
 * the UI menus within the application. It handles the creation, navigation and 
 * actions of menus. Automatically adds the menus to a JFrame.
 */
public class MenuController{
    private LevelController levelController; // A reference to the LevelController for the program
    private JFrame cacheNoisetteJFrame; // A reference to the main JFrame for the program
    private JPanel buttonMenu = new JPanel(); // Empty JPanel. Populated in createButtonMenu()

    /**
     * Constructs a {@code MenuController} instance.
     * Initializes a reference to a {@code Driver JFrame} where the menus will be added.
     * @param cacheNoisetteJFrame A reference to the main JFrame for the program
     */
    public MenuController(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
    }
    
    /**
     * Creates a {@code JLabel} with a specified text and preset formats.
     * @param titleString The text inside of the label.
     */
    public void createTitleLabel(String titleString){
        JLabel titleLabel = new JLabel(titleString);
        titleLabel.setFont(new Font("Segoe UI Black", 1, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setMaximumSize(new Dimension(200, 75));
        titleLabel.setMinimumSize(new Dimension(200, 75));
        titleLabel.setPreferredSize(new Dimension(200, 75));
        cacheNoisetteJFrame.add(titleLabel, BorderLayout.NORTH);

        // Refresh Frame
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }
    /**
     * Creates a {@code JPanel} containing the main navigation buttons for the user.
     * This method adds the buttons to the {@code JFrame CENTER} when called.
     */
    public void createButtonMenu(){
        buttonMenu = new JPanel(); // Holds all JSwing Objects for Main Menu
        JPanel buttonGroup = new JPanel(); // Holds both main menu buttons.
        JButton playButton = new JButton(); // Initiates the loading of Level 1
        JButton quitButton = new JButton(); // Exits Program with Status 0

        // Set initialPlayMenu layout to GridBagLayout
        buttonMenu.setLayout(new GridBagLayout());

        // Set buttonGroup Properties
        buttonGroup.setMaximumSize(new Dimension(400, 300));
        buttonGroup.setMinimumSize(new Dimension(400, 150));
        buttonGroup.setPreferredSize(new Dimension(400, 150));
        buttonGroup.setLayout(new GridLayout(2, 1, 0, 20));

        // Set playButton Properties
        playButton.setText("Click to Play");
        playButton.setMaximumSize(new Dimension(200, 75));
        playButton.setMinimumSize(new Dimension(200, 75));
        playButton.setPreferredSize(new Dimension(200, 75));
        playButton.addActionListener(e -> loadLevel(1));
        buttonGroup.add(playButton);

        // Set quitButton Properties
        quitButton.setText("Quit Game");
        quitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quitButton.setMaximumSize(new Dimension(200, 75));
        quitButton.setMinimumSize(new Dimension(200, 75));
        quitButton.setPreferredSize(new Dimension(200, 75));
        quitButton.addActionListener(e -> quitGame());
        buttonGroup.add(quitButton);

        // Add buttonGroup to initialPlayMenu
        buttonMenu.add(buttonGroup, new GridBagConstraints());
        cacheNoisetteJFrame.add(buttonMenu, BorderLayout.CENTER);

        // Refresh Frame
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }

    /**
     * Creates a {@code JMenuBar} with two menus ("Game" and "Select Level")
     * The Game menu contains a button to quit game. 
     * The Select Level menu dynamically populates based on the {@code .BMP} files 
     * found in the {@code assets/levels/} directory.
     */
    public void createMenuBar(){
        JMenuBar gameMenuBar = new JMenuBar();

        // Game Menu

        JMenu gameMenu = new JMenu("Game"); // Menu to hold extra functions buttons
        JMenuItem quitMenuItem = new JMenuItem("Quit Game"); // Button to quit the program

        gameMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        quitMenuItem.addActionListener(e -> quitGame());

        gameMenu.add(quitMenuItem);

        // Select Level Menu

        JMenu levelSelectorMenu = new JMenu("Select Level"); // Holds all levels that can be selected
        JMenuItem resetLevelMenuItem = new JMenuItem("Restart Level"); // Button to restart the level
        JMenuItem customLevelMenuItem = new JMenuItem("Load Custom Level..."); // Button to load custom levels

        levelSelectorMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        resetLevelMenuItem.addActionListener(e-> levelController.restartLevel());
        customLevelMenuItem.addActionListener(e -> loadCustomLevel());

        levelSelectorMenu.add(resetLevelMenuItem);
        levelSelectorMenu.add(customLevelMenuItem);

        // Find all level bmp files and add a button to menu for that level
        Path directoryPath = Paths.get("assets/levels/");
        String menuItemNameString = "";
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, "level*.bmp")) {
            for (Path entry : stream) {
                System.out.println("Found Level File: " + entry.getFileName());
                menuItemNameString = entry.getFileName().toString();
                int levelNumber = Integer.parseInt(menuItemNameString.replace("level", "").replace(".bmp", ""));
                JMenuItem levelMenuItem = new JMenuItem();
                levelMenuItem.setText("Level " + levelNumber);
                levelMenuItem.addActionListener(e -> loadLevel(levelNumber));
                levelSelectorMenu.add(levelMenuItem);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        gameMenuBar.add(gameMenu);
        gameMenuBar.add(levelSelectorMenu);

        // Set the JFrame's JMenuBar to gameMenuBar
        cacheNoisetteJFrame.setJMenuBar(gameMenuBar);

        // Refresh Frame
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
    }
    
    /**
     * Creates and displays a popup {@code JFrame} with the specified title and body content.
     * @param titleString The title of the JFrame, displayed in the banner.
     * @param bodyString The body text of the popup. Displayed in a textArea.
     */
    public void createPopUp(String titleString, String bodyString){
        JFrame popupFrame = new JFrame(titleString);
        JPanel panel = new JPanel();
        JButton closeButton = new JButton("Close Popup");
        JTextArea textArea = new JTextArea(2, 20);

        //Set JFrame Properties 
        popupFrame.setSize(new Dimension(300,200));
        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popupFrame.setLocationRelativeTo(null);

        // Add Action Listener To Button
        closeButton.addActionListener(e -> popupFrame.dispose());

        //Set textArea Properties
        textArea.setText(bodyString);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);

        //Add the contents to the panel and display panel
        panel.add(textArea);
        panel.add(closeButton);
        popupFrame.add(panel);
        popupFrame.setVisible(true);
    }
   
    /**
     * Exits the program with status code 0.
     * This method ensures all necessary cleanup is performed before termination.
     */
    public void quitGame(){
        System.out.println("Exiting Program, Goodbye!");
        System.exit(0);
    }
   
    /**
     * This method removes {@code buttonMenu} and calls {@code LevelController} to load a level.
     * @param levelNumber The number of the level to load
     */
    private void loadLevel(int levelNumber){
        cacheNoisetteJFrame.remove(buttonMenu);
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();

        levelController.loadLevel(levelNumber);
    }
  
    /**
     * Hides the main menu and triggers the loading of a custom level through 
     * the {@code LevelController}
     */
    private void loadCustomLevel(){
        cacheNoisetteJFrame.remove(buttonMenu);
        cacheNoisetteJFrame.revalidate();
        cacheNoisetteJFrame.repaint();
        levelController.loadCustomLevel();
    }

    /**
     * Sets the reference to a {@code LevelController} instance.
     * Allowing the MenuController to communicate with the LevelController.
     * @param levelController The LevelController instance to be referenced
     */
    public void setLevelController(LevelController levelController){
        this.levelController = levelController;
    }
}
