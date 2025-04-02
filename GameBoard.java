import javax.swing.JFrame;

public class GameBoard {
    protected JFrame cacheNoisetteJFrame;

    public GameBoard(JFrame cacheNoisetteJFrame){
        this.cacheNoisetteJFrame = cacheNoisetteJFrame;
    }


    public void placePiece(GamePiece piece, int x, int y){

    }

    public GamePiece getPieceAt(int x, int y){
        GamePiece piece = new GamePiece();
        return piece;
    }

    public void movePiece(GamePiece piece, Direction direction){

    }

    public void clearGameBoard(){
        
    }

    public void updateBoard(){

    }
}
