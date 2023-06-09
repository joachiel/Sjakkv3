package Project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.Game.Board;
import Project.Game.King;
import Project.Game.Knight;
import Project.Game.Piece;
import Project.Game.Queen;
import Project.Game.Square;

public class ChessAppTest {

    // Creating a board, and some pieces. The square doesnt matter in this case.
    Board board = new Board();
    Square Square = new Square(board, 0, 0);
    King king = new King(0, Square);
    Queen queen = new Queen(0, Square);
    Knight knight = new Knight(0, Square);

    

    private void assertEqualClasses(Class<? extends Piece> class1, Class<? extends Piece> class2) {
        if (class1 == null && class2 == null) {
            // If both classes are null, consider them equal
            return;
        }
        Assertions.assertEquals(class1, class2);
    }
    

    //Test the initial squares of the king and the queen
    @Test
    public void testInitialPosition() {
        assertEqualClasses(king.getClass(), board.getSquare(4,0).getOccupyingPiece().getClass());
        assertEqualClasses(queen.getClass(), board.getSquare(3, 0).getOccupyingPiece().getClass());
    }
    
    @Test
    public void testLegalMove() {
        Board board1 = new Board();
        
        //See if square is occupied first
        assertEquals(false, board1.getSquare(2, 5).isOccupied());


        //Moving the knight to that square
        board1.setSelectedPiece(board1.getSquare(1, 7).getOccupyingPiece());
        board1.moveTo(2, 5);

        //Checking if the knight is now occupying that square

        assertEqualClasses(knight.getClass(), board1.getSquare(2, 5).getOccupyingPiece().getClass());

    }
    @Test
    public void testIllegalMove() {
        Board board2 = new Board();

        //Seeing that the square is empty

        assertEquals(false, board2.getSquare(4, 4).isOccupied());

        // Trying to move the queen thorugh the pawn to the square 4, 4. That is an illegal move.
        board2.setSelectedPiece(board2.getSquare(3, 7).getOccupyingPiece());
        board2.moveTo(4, 4);

        // Seeing if that square is still empty, and that the queen is still on the original square
        assertEquals(false, board2.getSquare(4, 4).isOccupied());
        assertEqualClasses(queen.getClass(), board2.getSquare(3, 7).getOccupyingPiece().getClass());
    }
    @Test
    public void testBlackCheck() {
        Board board3 = new Board();

        //Set up a position where the black king is in check, and see if the CheckandMateDetector class detects it.

        board3.setSelectedPiece(board3.getSquare(4, 6).getOccupyingPiece());
        board3.moveTo(4, 4);

        board3.setSelectedPiece(board3.getSquare(3, 1).getOccupyingPiece());
        board3.moveTo(3, 3);

        board3.setSelectedPiece(board3.getSquare(5, 7).getOccupyingPiece());
        board3.moveTo(1, 3);

    }

    @Test
    public void testWhiteCheck() {
        Board board4 = new Board();

        //Set up a position where the black king is in check, and see if the CheckandMateDetector class detects it.

        board4.setSelectedPiece(board4.getSquare(4, 6).getOccupyingPiece());
        board4.moveTo(4, 4);

        board4.setSelectedPiece(board4.getSquare(4, 1).getOccupyingPiece());
        board4.moveTo(4, 3);

        board4.setSelectedPiece(board4.getSquare(3, 6).getOccupyingPiece());
        board4.moveTo(3, 4);

        board4.setSelectedPiece(board4.getSquare(5, 0).getOccupyingPiece());
        board4.moveTo(1, 4);

        
        assertEquals(true, board4.cmd.whiteInCheck());
    }
}
