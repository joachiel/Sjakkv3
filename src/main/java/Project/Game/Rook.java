package Project.Game;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int color, Square initSq) {
        super(color, initSq);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        ArrayList<Square> legalMoves = new ArrayList<Square>();
        Square[][] board = b.getBoard();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        int[] occups = getLinearOccupations(board, x, y);
        
        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }
        
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }
        
        return legalMoves;
    }
   
