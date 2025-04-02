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

public class MenuController{
    protected GameBoard gameBoard;
    protected LevelController levelController = new LevelController(gameBoard);
    private JFrame cacheNoisettJFrame;

    /**
     * Constructor for MenuController.
     * Creates a reference to a JFrame for the menus to be added to.
     */
    public MenuController(JFrame cacheNoisettJFrame, GameBoard gameBoard){
        this.cacheNoisettJFrame = cacheNoisettJFrame;
    }
    
    /**
     * This function creates a JLabel with the text string its provided with preset formats.
     * @param titleString The text inside of the label.
     * @return The JLabel after formatting
     */
    public void createTitleLabel(String titleString){
        JLabel titleLabel = new JLabel(titleString);
        titleLabel.setFont(new Font("Segoe UI Black", 1, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setMaximumSize(new Dimension(200, 75));
        titleLabel.setMinimumSize(new Dimension(200, 75));
        titleLabel.setPreferredSize(new Dimension(200, 75));
        cacheNoisettJFrame.add(titleLabel, BorderLayout.NORTH);

        // Refresh Frame
        cacheNoisettJFrame.revalidate();
        cacheNoisettJFrame.repaint();
    }
    
    public void createButtonMenu(){
        JPanel buttonMenu = new JPanel(); // Holds all JSwing Objects for Main Menu
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
        playButton.addActionListener(e -> levelController.loadLevel(1));
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
        cacheNoisettJFrame.add(buttonMenu, BorderLayout.CENTER);

        // Refresh Frame
        cacheNoisettJFrame.revalidate();
        cacheNoisettJFrame.repaint();
    }
    /**
     * This function creates a JMenuBar with two JMenus ("Game" and "Select Level").
     * These JMenus have many items, each with an event listener.
     * Select Level JMenu populates based on level.bmp files in assets/levels/
     */
    public void createMenuBar(){
        JMenuBar gameMenuBar = new JMenuBar();

        // Game Menu

        JMenu gameMenu = new JMenu("Game"); // Menu to hold extra functions buttons
        JMenuItem restartGameStateMenuItem = new JMenuItem("Restart Game State"); // Button to reset program to initial state
        JMenuItem quitMenuItem = new JMenuItem("Quit Game"); // Button to quit the program

        gameMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        restartGameStateMenuItem.addActionListener(e -> System.out.println("Restarting Game State"));
        quitMenuItem.addActionListener(e -> quitGame());

        gameMenu.add(restartGameStateMenuItem);
        gameMenu.add(quitMenuItem);

        // Select Level Menu

        JMenu levelSelectorMenu = new JMenu("Select Level"); // Holds all levels that can be selected
        JMenuItem resetLevelMenuItem = new JMenuItem("Restart Level"); // Button to restart the level
        JMenuItem customLevelMenuItem = new JMenuItem("Load Custom Level..."); // Button to load custom levels

        levelSelectorMenu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        resetLevelMenuItem.addActionListener(e-> System.out.println("Resetting Level"));
        customLevelMenuItem.addActionListener(e -> System.out.println("Loading Custom Level"));

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
                levelMenuItem.addActionListener(e -> levelController.loadLevel(levelNumber));
                levelSelectorMenu.add(levelMenuItem);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        gameMenuBar.add(gameMenu);
        gameMenuBar.add(levelSelectorMenu);

        // Set the JFrame's JMenuBar to gameMenuBar
        cacheNoisettJFrame.setJMenuBar(gameMenuBar);

        // Refresh Frame
        cacheNoisettJFrame.revalidate();
        cacheNoisettJFrame.repaint();
    }
    public void quitGame(){
        System.out.println("Exiting Program, Goodbye!");
        System.exit(0);
    }
}
