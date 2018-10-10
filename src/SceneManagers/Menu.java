package SceneManagers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Main;
import sample.State;

import java.io.IOException;

public class Menu {

    public Menu(Stage stage){
        Main.playSoundtrack("Menu");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Scenes/menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("TicTacToe");
        stage.setScene(new Scene(root, 900, 500));

        Button sngl = (Button) stage.getScene().lookup("#sngl");
        Button set = (Button) stage.getScene().lookup("#set");

        set.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Settings(stage);
            }
        });

        sngl.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Main.state = State.INGAME;

                new Game(stage);

            }
        });
    }
}
