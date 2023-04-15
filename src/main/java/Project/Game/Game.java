package Project.Game;

public class Game {
    public static void main(String[] args) {
        Board board1 = new Board();
        board1.initializePieces();
        System.out.println(board1.board[1][0].getOccupyingPiece().getLegalMoves(board1));


        }
    }

