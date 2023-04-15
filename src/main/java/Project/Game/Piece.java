package Project.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private int color;
    private Square currentSquare;
    
    Piece(int color, Square square) {
        if(color == 0 && color == 1){
            this.color = color;
        }
        this.currentSquare = square;
    }

    public boolean move(Square square){
        Piece occupyingPiece = square.getOccupyingPiece();

        if(occupyingPiece != null){
            if(occupyingPiece.getColor() == this.color){
                return false;
            }
            else{
                square.capture(this);
            }
        }

        currentSquare.removePiece();
        this.currentSquare = square;
        currentSquare.setOccupyingPiece(this);
        return true;
    }

    public Square getPosition(){
        return currentSquare;
    }

    public void setPosition(Square square){
        this.currentSquare = square;
    }


    public int getColor(){
        return this.color;
    }

    public List<Square> getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;
        
        for (int i = 0; i < y; i++) {
            if (board[x][i].isOccupied()) {
                if (board[x][i].getOccupyingPiece().getColor() != this.color) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[x][i].isOccupied()) {
                if (board[x][i].getOccupyingPiece().getColor() != this.color) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[i][y].isOccupied()) {
                if (board[i][y].getOccupyingPiece().getColor() != this.color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[i][y].isOccupied()) {
                if (board[i][y].getOccupyingPiece().getColor() != this.color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }
        
        int[] occups = {lastYabove, lastYbelow, lastXleft, lastXright};

        ArrayList<Square> linearOccup = new ArrayList<Square>();
        
        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) linearOccup.add(board[x][i]);
        }
        
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) linearOccup.add(board[i][y]);
        }

        return linearOccup;
    }

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        ArrayList<Square> diagOccup = new ArrayList<Square>();
        
        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;
        
        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[yNW][xNW]);
                    break;
                }
            } else {
                diagOccup.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }
        
        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[ySW][xSW]);
                    break;
                }
            } else {
                diagOccup.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }
        
        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[ySE][xSE]);
                    break;
                }
            } else {
                diagOccup.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }
        
        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[yNE][xNE]);
                    break;
                }
            } else {
                diagOccup.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }
        
        return diagOccup;
    }
    
    public abstract List<Square> getLegalMoves(Board b);

    public abstract char getSymbol();
}
