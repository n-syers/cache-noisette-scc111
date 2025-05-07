import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
/**
 * This class represents a {@code Movement Button} instance, which extends the {@code JButton} class and implements the {@code ActionListener} interface.
 * This class defines the behaviour, attributes, and actions a MovementButton instance can perform within the game. 
 * 
 * @see JButton
 * @see ActionListener
 */
public class MovementButton extends JButton implements ActionListener{
    Picture arrowPicture; // Holds an instance of Picture for JButton
    GameBoard gameBoard; // Holds a reference to GameBoard
    Direction direction; // Holds the direction enum of the arrow

    /**
     * This enum is used to indicate the size of the MovementButton
     * and what arrow to show.
     */
    public enum Size{
        LARGE,
        SMALL
    };
    /**
     * Constructs an instance of {@code MovementButton}, while initialising {@code gameBoard}, {@code direction}, and {@code arrowPicture}
     * This creates a {@code JButton} with formatting and assigns it an action listener.
     * @param gameBoard The GameBoard instance to be referenced
     * @param direction The Direction enum of the arrow
     * @param arrowSize The Size enum of the arrow
     */
    public MovementButton(GameBoard gameBoard, Direction direction, Size arrowSize){
        this.gameBoard = gameBoard;
        this.direction = direction;
        switch (arrowSize) {
            case LARGE:
                arrowPicture = new Picture("./assets/icons/Arrow.png", direction.getValue(direction));
                this.setPreferredSize(new Dimension(100,100));
                break;
            case SMALL:
                arrowPicture = new Picture("./assets/icons/Arrow.png", direction.getValue(direction));
                this.setPreferredSize(new Dimension(100,100));
                break;
            default:
                System.out.println("An Error Occured: Could Not Find Arrow Size, Set To Small");
                arrowPicture = new Picture("./assets/icons/Arrow.png", direction.getValue(direction));
                break;
        }
        this.setBorder(null);
        this.setIcon(arrowPicture);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       GamePiece piece = gameBoard.getSelectedGamePiece();
       if(!(piece instanceof Squirrel)){
            System.out.println("An Error Occured: No Squirrel Selected");
            return;
        }
        Squirrel squirrel = (Squirrel) piece;
        squirrel.move(direction);
    }
}
