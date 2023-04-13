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
            board[1][i].setOccupyingPiece(new Pawn());
            board[6][i].setOccupyingPiece(new Pawn());   
        }

        board[7][3].setOccupyingPiece(new Queen());
        board[0][3].setOccupyingPiece(new Queen());

        King bk = new King();
        King wk = new King();
        board[0][4].setOccupyingPiece(bk);
        board[7][4].setOccupyingPiece(wk);

        board[0][0].setOccupyingPiece(new Rook());
        board[0][7].setOccupyingPiece(new Rook());
        board[7][0].setOccupyingPiece(new Rook());
        board[7][7].setOccupyingPiece(new Rook());

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
