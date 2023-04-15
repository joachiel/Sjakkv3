package Project.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import Project.Game.Board;
import Project.Game.Rook;
import Project.Game.Square;

public class ChessController {

    @FXML GridPane grid;

    @FXML Pane pane00, pane01, pane02, pane03, pane04, pane05, pane06, pane07, pane10, pane11, pane12, pane13, pane14, pane15, pane16, pane17, 
               pane20, pane21, pane22, pane23, pane24, pane25, pane26, pane27, pane30, pane31, pane32, pane33, pane34, pane35, pane36, pane37,
               pane40, pane41, pane42, pane43, pane44, pane45, pane46, pane47, pane50, pane51, pane52, pane53, pane54, pane55, pane56, pane57,
               pane60, pane61, pane62, pane63, pane64, pane65, pane66, pane67, pane70, pane71, pane72, pane73, pane74, pane75, pane76, pane77;
    
    @FXML private ImageView wr1, wr2, wn1, wn2;

    Board board1 =  new Board();
    ImageView selectedPiece = null;
    Pane selectedSquare = null;
    Pane previousSquare = null;


    @FXML
    public void moveTo(MouseEvent event){
        if(this.selectedPiece != null){
            this.previousSquare = (Pane) selectedPiece.getParent();
            this.selectedSquare = (Pane) event.getSource();
            if(this.previousSquare != this.selectedSquare){
                String pane = selectedSquare.toString().replace("Pane[id=pane", "").replace("]", "");
                Integer cordY = Integer.parseInt(String.valueOf(pane.charAt(0)));
                Integer cordX = Integer.parseInt(String.valueOf(pane.charAt(1)));
                List<Square> legalMoves = board1.getSelectedPiece().getLegalMoves(board1);
                if(legalMoves.contains(board1.board[cordX][cordY]) && board1.getSelectedPiece().move(board1.board[cordX][cordY]) == true){
                        board1.getSelectedPiece().move(board1.board[cordX][cordY]);
                        previousSquare.getChildren().remove(selectedPiece);
                        if(selectedSquare.getChildren() != null){
                            selectedSquare.getChildren().removeAll();
                        }
                        selectedSquare.getChildren().add(selectedPiece);
                        this.previousSquare = null;
                        this.selectedSquare = null;
                        this.selectedPiece = null;
                }
            }
        }
    }

    @FXML
    public void selectPiece(MouseEvent event) {
        if(this.selectedPiece == null){
            this.selectedPiece = (ImageView) event.getSource();
            Integer cordY = Integer.parseInt(String.valueOf(selectedPiece.getParent().toString().replace("Pane[id=pane", "").replace("]", "").charAt(0)));
            Integer cordX = Integer.parseInt(String.valueOf(selectedPiece.getParent().toString().replace("Pane[id=pane", "").replace("]", "").charAt(1)));
            board1.setSelectedPiece(board1.board[cordX][cordY].getOccupyingPiece());
            System.out.println(selectedPiece);
            List<Square> legalMoves = board1.getSelectedPiece().getLegalMoves(board1);
            for (Square s : legalMoves) {
                System.out.println("X: "+s.getX());
                System.out.println("Y: "+s.getY());
            }
            System.out.println(board1.getSelectedPiece().getPosition().getX());
            System.out.println(board1.getSelectedPiece().getPosition().getY());
        }
    }
}

