package Project.Game;



import java.util.List;
import java.util.LinkedList;

public class Pawn extends Piece {
    private boolean wasMoved;
    
    public Pawn(int color, Square initSq) {
        super(color, initSq);
    }
    
    @Override
    public boolean move(Square fin) {
        boolean b = super.move(fin);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        
        Square[][] board = b.getBoard();
        
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int c = this.getColor();
        
        if (c == 0) {
            if (!wasMoved) {
                if (!board[y+2][x].isOccupied()) {
                    legalMoves.add(board[y+2][x]);
                }
            }
            
            if (y+1 < 8) {
                if (!board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+1][x]);
                }
            }
            
            if (x+1 < 8 && y+1 < 8) {
                if (board[y+1][x+1].isOccupied()) {
                    legalMoves.add(board[y+1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y+1 < 8) {
                if (board[y+1][x-1].isOccupied()) {
                    legalMoves.add(board[y+1][x-1]);
                }
            }
        }
        
        if (c == 1) {
            if (!wasMoved) {
                if (!board[y-2][x].isOccupied()) {
                    legalMoves.add(board[y-2][x]);
                }
            }
            
            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-1][x]);
                }
            }
            
            if (x+1 < 8 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()) {
                    legalMoves.add(board[y-1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()) {
                    legalMoves.add(board[y-1][x-1]);
                }
            }
        }
        
        return legalMoves;
    }
    public char getSymbol () {
        return 'p';
    }
}
