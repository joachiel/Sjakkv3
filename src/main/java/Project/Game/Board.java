package Project.Game;

import java.util.LinkedList;
import java.util.List;

public class Board {
    //Representation of the board
    public Square[][] board;

    public LinkedList<Piece> whitePieces;
    public LinkedList<Piece> blackPieces;

    private boolean whiteTurn;

    private Piece selectedPiece;
    private int selectedX;
    private int selectedY;

    private CheckAndMateDetector cmd;

    public Board(){
        board = new Square[8][8];
        whitePieces = new LinkedList<>();
        blackPieces = new LinkedList<>();
        whiteTurn = true;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = new Square(this, x, y);
            }
        }

        initializePieces();
    }

    public void initializePieces() {
        for (int i = 0; i < 8; i++){
            board[i][1].setOccupyingPiece(new Pawn(1, board[i][1])); //Initialize the black pawns
            board[i][6].setOccupyingPiece(new Pawn(0, board[i][6])); //Initialize the white pawns
        }
        board[3][0].setOccupyingPiece(new Queen(1, board[3][0])); // The black queen
        board[3][7].setOccupyingPiece(new Queen(0, board[3][7])); // The white queen
         

        King bk = new King(1, board[4][0]); 
        King wk = new King(0, board[4][7]);
        board[4][0].setOccupyingPiece(bk); // The Black king
        board[4][7].setOccupyingPiece(wk); // The white king

        board[0][0].setOccupyingPiece(new Rook(1, board[0][0]));
        board[7][0].setOccupyingPiece(new Rook(1, board[7][0]));
        board[0][7].setOccupyingPiece(new Rook(0, board[0][7]));
        board[7][7].setOccupyingPiece(new Rook(0, board[7][7]));

        board[1][0].setOccupyingPiece(new Knight(1, board[1][0]));
        board[6][0].setOccupyingPiece(new Knight(1, board[6][0]));
        board[1][7].setOccupyingPiece(new Knight(0, board[1][6]));
        board[6][7].setOccupyingPiece(new Knight(0, board[6][7]));

        board[2][0].setOccupyingPiece(new Bishop(1, board[2][0]));
        board[5][0].setOccupyingPiece(new Bishop(1, board[5][0]));
        board[2][7].setOccupyingPiece(new Bishop(0, board[2][7]));
        board[5][7].setOccupyingPiece(new Bishop(0, board[5][7]));

        for(int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++){
                blackPieces.add(board[j][i].getOccupyingPiece());
                whitePieces.add(board[7-j][i].getOccupyingPiece());
            }
        }
        // cmd = new CheckAndMateDetector(this, whitePieces, blackPieces, wk, bk);
    }
    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        System.out.println("  ----------------");
        for (int y = 0; y < 8; y++) {
            System.out.print(y + "|");
            for (int x = 0; x < 8; x++) {
                Piece p = board[x][y].getOccupyingPiece();
                if (p == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(p.getSymbol() + " ");
                }
            }
            System.out.println("|" + y);
        }
        System.out.println("  ----------------");
        System.out.println("  0 1 2 3 4 5 6 7");
    }
    
    
    
    public Square[][] getBoard(){
        return this.board;
    }

    public boolean getTurn(){
        return whiteTurn;
    }

    public void setSelectedPiece(Piece p){
        this.selectedPiece = p;
    }

    public Piece getSelectedPiece(){
        return this.selectedPiece;
    }
    public static void main(String[] args) {
        Board board1 =  new Board();
        System.out.println(board1.board[1][0].getOccupyingPiece().move(board1.board[1][1]));
        board1.printBoard();
}}
