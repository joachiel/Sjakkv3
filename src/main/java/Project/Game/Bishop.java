package Project.Game;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq) {
        super(color, initSq);
    }
    
    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getBoard();
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        return getDiagonalOccupations(board, x, y);
    }
    public char getSymbol () {
        return 'b';
    }
}
