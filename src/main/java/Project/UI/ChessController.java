package Project.UI;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import Project.Game.Board;


public class ChessController {

    @FXML GridPane grid;

    @FXML Pane pane00, pane01, pane02, pane03, pane04, pane05, pane06, pane07, pane10, pane11, pane12, pane13, pane14, pane15, pane16, pane17, 
               pane20, pane21, pane22, pane23, pane24, pane25, pane26, pane27, pane30, pane31, pane32, pane33, pane34, pane35, pane36, pane37,
               pane40, pane41, pane42, pane43, pane44, pane45, pane46, pane47, pane50, pane51, pane52, pane53, pane54, pane55, pane56, pane57,
               pane60, pane61, pane62, pane63, pane64, pane65, pane66, pane67, pane70, pane71, pane72, pane73, pane74, pane75, pane76, pane77;
    
    @FXML ImageView br1, bn1, bb1, bq, bk, bb2, bn2, br2, bp1, bp11, bp111, bp1111, bp11111, bp111111, bp1111111, bp11111111,
                    wr1, wn1, wb1, wq, wk, wb2, wn2, wr2, wp1, wp2, wp21, wp211, wp212, wp2121, wp21211, wp212111;
    
    @FXML TextArea checkMate, moveOut;

    @FXML Button btn;

    Board board;

    boolean started = false;

    ImageView selectedPiece = null;
    Pane selectedSquare = null;
    Pane previousSquare = null;



    @FXML
    public void initChess(){
        board = new Board();
    }

    @FXML
    public void moveTo(MouseEvent event) throws IOException{
        if(board.getSelectedPiece() != null && this.selectedPiece != null){
            this.previousSquare = (Pane) this.selectedPiece.getParent();
            this.selectedSquare = (Pane) event.getSource();
            if(this.previousSquare != this.selectedSquare){
                String pane = selectedSquare.toString().replace("Pane[id=pane", "").replace("]", "");
                Integer cordY = Integer.parseInt(String.valueOf(pane.charAt(0)));
                Integer cordX = Integer.parseInt(String.valueOf(pane.charAt(1)));
                board.moveTo(cordX, cordY);
                if(board.moved == true){
                String move = selectedPiece.getId() + "-" + cordX + ":" + cordY;
                board.writeStateToFile("/moves.txt", move);
                moveOut.setText(board.readStateFromFile("/moves.txt"));
                previousSquare.getChildren().remove(selectedPiece);
                selectedSquare.getChildren().add(selectedPiece);
                        if(selectedSquare.getChildren().size() > 1){
                            selectedSquare.getChildren().get(0).setVisible(false);
                            selectedSquare.getChildren().remove(0);
                        }
                this.previousSquare = null;
                this.selectedSquare = null;
                this.selectedPiece = null;   
                board.moved = false;
                if(board.whiteCheckMate){
                    checkMate.setText("White has been checkmated");
                    checkMate.setVisible(true);
                    btn.setVisible(true);
                    
                }
                if(board.blackCheckMate){
                    checkMate.setVisible(true);
                    checkMate.setText("Black has been checkmated");
                    btn.setVisible(true);
                }
                    }
                    else{
                        board.setSelectedPiece(null);
                        this.selectedPiece = null;
                    }
                }
            }
        }

    @FXML
    public void selectPiece(MouseEvent event) {
        if(this.started == false){
            initChess();
            this.started = true;
        }
        if(board.getSelectedPiece() == null && this.selectedPiece == null){
            this.selectedPiece = (ImageView) event.getSource();
            Integer cordY = Integer.parseInt(String.valueOf(selectedPiece.getParent().toString().replace("Pane[id=pane", "").replace("]", "").charAt(0)));
            Integer cordX = Integer.parseInt(String.valueOf(selectedPiece.getParent().toString().replace("Pane[id=pane", "").replace("]", "").charAt(1)));
            board.selectPiece(cordX, cordY);
            if(board.getSelectedPiece() == null){
                this.selectedPiece = null;
            }
        }
    }

    @FXML
    public void restartGame(){
        board = null;
        board = new Board();
        board.printBoard();
    }
}

