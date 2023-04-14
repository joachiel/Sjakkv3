package Project.Game;



import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    public Queen(int color, Square initSq) {
        super(color, initSq);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getBoard();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        
        
        List<Square> diagMoves = getDiagonalOccupations(board, x, y);
        List<Square> linearMoves = getLinearOccupations(board, x, y);
        
        legalMoves.addAll(diagMoves);
        legalMoves.addAll(linearMoves);

        
        return legalMoves;
    }
    
}
