package Project.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Chess");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/ChessApp.fxml"));

        try {
            primaryStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        primaryStage.show();

    }
}
