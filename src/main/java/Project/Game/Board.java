package Project.Game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Project.Util.FileHelper;
import javafx.scene.input.MouseEvent;

public class Board {
    //Representation of the board
    public Square[][] board;

    public LinkedList<Piece> whitePieces;
    public LinkedList<Piece> blackPieces;
    public List<Square> movable;

    public boolean whiteTurn;
    public boolean whiteCheckMate = false;
    public boolean blackCheckMate = false;
    public boolean moved = false;

    private Piece selectedPiece;
    private int selectedX;
    private int selectedY;

    private CheckAndMateDetector cmd;
    private moveWrite write;

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
        board[1][7].setOccupyingPiece(new Knight(0, board[1][7]));
        board[6][7].setOccupyingPiece(new Knight(0, board[6][7]));

        board[2][0].setOccupyingPiece(new Bishop(1, board[2][0]));
        board[5][0].setOccupyingPiece(new Bishop(1, board[5][0]));
        board[2][7].setOccupyingPiece(new Bishop(0, board[2][7]));
        board[5][7].setOccupyingPiece(new Bishop(0, board[5][7]));

        for(int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++){
                blackPieces.add(board[j][i].getOccupyingPiece());
                whitePieces.add(board[j][7-i].getOccupyingPiece());
            }
        }
        cmd = new CheckAndMateDetector(this, whitePieces, blackPieces, wk, bk);
        write = new moveWrite();
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
    public Square getSquare(int x, int y) {
        return this.board[x][y];

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

    public void selectPiece(int x, int y) {
        Square sq = this.board[x][y];
        if(sq.isOccupied()){
            this.selectedPiece = sq.getOccupyingPiece();
            if(selectedPiece.getColor() == 0 && !whiteTurn){
                return;
            }
            if(selectedPiece.getColor() == 1 && whiteTurn){
                return;
            }
        }
    }

    public void moveTo(int x, int y){
        Square sq = this.board[x][y];
        if (this.selectedPiece != null) {
            if (this.selectedPiece.getColor() == 1 && whiteTurn){
                return;
                }
            if (this.selectedPiece.getColor() == 0 && !whiteTurn){
                return;
                }
            }

            List<Square> legalMoves = this.selectedPiece.getLegalMoves(this);
            movable = cmd.getAllowableSquares(whiteTurn);

            if(legalMoves.contains(sq) && movable.contains(sq) && cmd.testMove(selectedPiece, sq) && selectedPiece.move(sq)){
                selectedPiece.move(sq);
                String move = this.selectedPiece.getClass()+ ":" + sq.getX() + ", " + sq.getY();
                System.out.println(move.replace("class Project.Game.", ""));
                write.writeMovesToFile("moves.txt", move.replace("class Project.Game.", ""));
                cmd.update();
                if(cmd.blackCheckMated()){
                    selectedPiece = null;
                    this.blackCheckMate = true;
                    this.moved = true;
                }
                else if(cmd.whiteCheckMated()){
                    this.selectedPiece = null;
                    this.whiteCheckMate = true;
                    this.moved = true;
                }
                else{
                    this.selectedPiece = null;
                    this.whiteTurn = !whiteTurn;
                    this.moved = true;
                    movable = cmd.getAllowableSquares(whiteTurn);
                }
            }
        else {
            this.selectedPiece = null;
        }
    }
}