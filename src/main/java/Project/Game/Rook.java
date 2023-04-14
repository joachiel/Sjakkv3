package Project.Game;

import java.util.List;

public class Rook extends Piece {

    public Rook(int color, Square initSq) {
        super(color, initSq);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        
        Square[][] board = b.getBoard();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        return getLinearOccupations(board, x, y)
    }}
   
