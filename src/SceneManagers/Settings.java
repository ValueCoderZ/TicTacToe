package SceneManagers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings {

    public Settings(Stage stage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Scenes/settings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 900, 500));

        Button back = (Button) stage.getScene().lookup("#bck");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Menu(stage);
            }
        });
    }
}
