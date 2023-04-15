package Project.Game;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    public King(int color, Square initSq) {
        super(color, initSq);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        
        Square[][] board = b.getBoard();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[x + i][y + k].isOccupied() || 
                                board[x + i][y + k].getOccupyingPiece().getColor() 
                                != this.getColor()) {
                            legalMoves.add(board[x + i][y + k]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        
        return legalMoves;
    }
    public char getSymbol () {
        return 'k';
    }

}
