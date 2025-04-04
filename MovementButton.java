import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MovementButton extends JButton implements ActionListener{
    Picture arrowPicture;
    GameBoard gameBoard;
    Direction direction;

    public enum Size{
        LARGE,
        SMALL
    };

    public MovementButton(GameBoard gameBoard, Direction direction, Size arrowSize){
        this.gameBoard = gameBoard;
        this.direction = direction;
        switch (arrowSize) {
            case LARGE:
                arrowPicture = new Picture("assets\\icons\\BigArrow.png", direction.getValue(direction));
                this.setPreferredSize(new Dimension(600,100));
                break;
            case SMALL:
                arrowPicture = new Picture("assets\\icons\\Arrow.png", direction.getValue(direction));
                this.setPreferredSize(new Dimension(100,400));
                break;
            default:
                System.out.println("An Error Occured: Could Not Find Arrow Size, Set To Small");
                arrowPicture = new Picture("assets\\icons\\Arrow.png", direction.getValue(direction));
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
