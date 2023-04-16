package Project;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Project.Game.Board;
import Project.Game.King;
import Project.Game.Piece;
import Project.Game.Queen;
import Project.Game.Square;

public class ChessAppTest {
    Board board = new Board();
    Square Square = new Square(board, 0, 0);
    King king = new King(0, Square);
    Queen queen = new Queen(0, Square);

    private void assertEqual(Class<? extends King> class1, Piece occupyingPiece, Class<? extends ChessAppTest> class2) {
    }
    private void assertEqual2(Class<? extends Queen> class1, Class<? extends Piece> class2) {
    }
    @Test
    public void testInitialPosition() {
        assertEqual(king.getClass(), board.board[4][0].getOccupyingPiece(),getClass());
        assertEqual2(queen.getClass(), board.board[3][0].getOccupyingPiece().getClass());
    }
    
    @Test
    public void testLegalMoves() {
        //assertEquals(false, board.moveTo());

    }
}
