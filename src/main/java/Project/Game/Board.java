package Project.Game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    //Representation of the board
    public Square[][] board;

    public List<Piece> whitePieces;
    public List<Piece> blackPieces;

    private boolean whiteTurn;

    private Piece selectedPiece;
    private int selectedX;
    private int selectedY;

    public Board(){
        board = new Square[8][8];
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
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
            board[1][i].setOccupyingPiece(new Pawn(1, board[1][i])); //Initialize the black pawns
            board[6][i].setOccupyingPiece(new Pawn(0, board[6][i])); //Initialize the white pawns
        }

        board[7][3].setOccupyingPiece(new Queen()); // The white queen
        board[0][3].setOccupyingPiece(new Queen()); // The black queen

        King bk = new King(); 
        King wk = new King();
        board[0][4].setOccupyingPiece(bk); // The Black king
        board[7][4].setOccupyingPiece(wk); // The white king

        board[0][0].setOccupyingPiece(new Rook(1, board[0][0]));
        board[0][7].setOccupyingPiece(new Rook(1, board[0][7]));
        board[7][0].setOccupyingPiece(new Rook(0, board[7][0]));
        board[7][7].setOccupyingPiece(new Rook(0, board[7][7]));

        board[0][1].setOccupyingPiece(new Knight());
        board[0][6].setOccupyingPiece(new Knight());
        board[6][1].setOccupyingPiece(new Knight());
        board[7][6].setOccupyingPiece(new Knight());

        board[0][2].setOccupyingPiece(new Bishop());
        board[0][5].setOccupyingPiece(new Bishop());
        board[7][2].setOccupyingPiece(new Bishop());
        board[7][5].setOccupyingPiece(new Bishop());

        for(int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++){
                blackPieces.add(board[i][j].getOccupyingPiece());
                whitePieces.add(board[7-i][j].getOccupyingPiece());
            }
        }
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


}
