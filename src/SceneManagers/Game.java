package SceneManagers;

import KIClasses.Easy;
import KIClasses.Hard;
import KIClasses.Medium;
import KIClasses.Ultra;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Color;
import sample.Difficulty;
import sample.Main;
import sample.State;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Game {

    public Difficulty difficulty;

    public  Game(Stage stage){
        buildDifMenu(stage);
    }

    public void buildDifMenu(Stage stage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Scenes/dif_menu.fxml"));
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

        Button easy = (Button) stage.getScene().lookup("#easy");
        easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difficulty = Difficulty.EASY;

                if(new Random().nextInt(200) > 100){
                    Main.waiting = true;
                }

                buildInGame(stage);
            }
        });

        Button medium = (Button) stage.getScene().lookup("#medium");
        medium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difficulty = Difficulty.MEDIUM;

                if(new Random().nextInt(200) > 100){
                    Main.waiting = true;
                }

                buildInGame(stage);
            }
        });

        Button hard = (Button) stage.getScene().lookup("#hard");
        hard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difficulty = Difficulty.HARD;
                Hard.intialize();

                if(new Random().nextInt(200) > 100){
                    Main.waiting = true;
                }

                buildInGame(stage);
            }
        });

        Button ultra = (Button) stage.getScene().lookup("#ultra");
        ultra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difficulty = Difficulty.ULTRA;
                Ultra.intialize();

                if(new Random().nextInt(200) > 100){
                    Main.waiting = true;
                }

                buildInGame(stage);
            }
        });
    }

    public void buildInGame(Stage stage){
        Main.playSoundtrack("InGame");


        Main.places.clear();
        Main.freelist.clear();

        Main.places.put("btn_1_1", Color.NEUTRAL); Main.freelist.add("btn_1_1");
        Main.places.put("btn_1_2", Color.NEUTRAL); Main.freelist.add("btn_1_2");
        Main.places.put("btn_1_3", Color.NEUTRAL); Main.freelist.add("btn_1_3");
        Main.places.put("btn_2_1", Color.NEUTRAL); Main.freelist.add("btn_2_1");
        Main.places.put("btn_2_2", Color.NEUTRAL); Main.freelist.add("btn_2_2");
        Main.places.put("btn_2_3", Color.NEUTRAL); Main.freelist.add("btn_2_3");
        Main.places.put("btn_3_1", Color.NEUTRAL); Main.freelist.add("btn_3_1");
        Main.places.put("btn_3_2", Color.NEUTRAL); Main.freelist.add("btn_3_2");
        Main.places.put("btn_3_3", Color.NEUTRAL); Main.freelist.add("btn_3_3");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Scenes/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 900, 500));

        Button back = (Button) stage.getScene().lookup("#bck");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Main.state == State.AFTER){
                    Main.state = State.MENU;
                    Main.waiting = false;
                    new Menu(stage);
                }
            }
        });
        stage.getScene().setEventDispatcher(new EventDispatcher() {
            @Override
            public Event dispatchEvent(Event event, EventDispatchChain tail) {
                if(event instanceof MouseEvent){
                    MouseEvent event1 = (MouseEvent) event;
                    if(event1.isPrimaryButtonDown()){
                        if(event.getTarget().getClass().equals(Button.class)) {
                            if(Main.state == State.INGAME) {
                                Button btn = (Button) event.getTarget();
                                if (!btn.isDisabled() && !Main.waiting && Main.freelist.contains(btn.getId())) {
                                    Main.waiting = true;
                                    btn.setOpacity(0.5);
                                    btn.setStyle("-fx-background-color: #3de23d");

                                    Main.places.put(btn.getId(), Color.GREEN);
                                    if (Main.freelist.contains(btn.getId())) {
                                        Main.freelist.remove(btn.getId());
                                    }

                                    checkForWin(stage);
                                }
                            }
                        }
                    }
                }
                return null;
            }
        });
        if(Main.waiting){
            launchKI(stage, true);
        }
    }

    private void checkForWin(Stage stage){
        if(Main.freelist.size() > 0){
            if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_1_2", "btn_1_3") || checkPositions(Color.RED, Main.places, "btn_2_1", "btn_2_2", "btn_2_3") || checkPositions(Color.RED, Main.places, "btn_3_1", "btn_3_2", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_1", "btn_3_1") || checkPositions(Color.RED, Main.places, "btn_1_2", "btn_2_2", "btn_3_2") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_3", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_2", "btn_3_3") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_2", "btn_3_1")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }
            // GREEN
            else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_1_2", "btn_1_3") || checkPositions(Color.GREEN, Main.places, "btn_2_1", "btn_2_2", "btn_2_3") || checkPositions(Color.GREEN, Main.places, "btn_3_1", "btn_3_2", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_2_1", "btn_3_1") || checkPositions(Color.GREEN, Main.places, "btn_1_2", "btn_2_2", "btn_3_2") || checkPositions(Color.GREEN, Main.places, "btn_1_3", "btn_2_3", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_2_2", "btn_3_3") || checkPositions(Color.GREEN, Main.places, "btn_1_3", "btn_2_2", "btn_3_1")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else{
                launchKI(stage, false);
                if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_1_2", "btn_1_3") || checkPositions(Color.RED, Main.places, "btn_2_1", "btn_2_2", "btn_2_3") || checkPositions(Color.RED, Main.places, "btn_3_1", "btn_3_2", "btn_3_3")){
                    Main.state = State.AFTER;
                    Text text = (Text) stage.getScene().lookup("#winner_na");
                    text.setText("You Lose!");
                    stage.getScene().lookup("#winner_na").setVisible(true);
                    stage.getScene().lookup("#bck").setVisible(true);
                    fetchWin(stage, Color.RED);
                }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_1", "btn_3_1") || checkPositions(Color.RED, Main.places, "btn_1_2", "btn_2_2", "btn_3_2") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_3", "btn_3_3")){
                    Main.state = State.AFTER;
                    Text text = (Text) stage.getScene().lookup("#winner_na");
                    text.setText("You Lose!");
                    stage.getScene().lookup("#winner_na").setVisible(true);
                    stage.getScene().lookup("#bck").setVisible(true);
                    fetchWin(stage, Color.RED);
                }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_2", "btn_3_3") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_2", "btn_3_1")){
                    Main.state = State.AFTER;
                    Text text = (Text) stage.getScene().lookup("#winner_na");
                    text.setText("You Lose!");
                    stage.getScene().lookup("#winner_na").setVisible(true);
                    stage.getScene().lookup("#bck").setVisible(true);
                    fetchWin(stage, Color.RED);
                }else if(!(Main.freelist.size() > 0)){
                    Main.state = State.AFTER;
                    Text text = (Text) stage.getScene().lookup("#winner_na");
                    text.setText("Draw!");
                    stage.getScene().lookup("#winner_na").setVisible(true);
                    stage.getScene().lookup("#bck").setVisible(true);
                    fetchWin(stage, Color.NEUTRAL);
                }
            }
        }else{
            if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_1_2", "btn_1_3") || checkPositions(Color.RED, Main.places, "btn_2_1", "btn_2_2", "btn_2_3") || checkPositions(Color.RED, Main.places, "btn_3_1", "btn_3_2", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_1", "btn_3_1") || checkPositions(Color.RED, Main.places, "btn_1_2", "btn_2_2", "btn_3_2") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_3", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }else if(checkPositions(Color.RED, Main.places, "btn_1_1", "btn_2_2", "btn_3_3") || checkPositions(Color.RED, Main.places, "btn_1_3", "btn_2_2", "btn_3_1")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Lose!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.RED);
            }
            // GREEN
            else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_1_2", "btn_1_3") || checkPositions(Color.GREEN, Main.places, "btn_2_1", "btn_2_2", "btn_2_3") || checkPositions(Color.GREEN, Main.places, "btn_3_1", "btn_3_2", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_2_1", "btn_3_1") || checkPositions(Color.GREEN, Main.places, "btn_1_2", "btn_2_2", "btn_3_2") || checkPositions(Color.GREEN, Main.places, "btn_1_3", "btn_2_3", "btn_3_3")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else if(checkPositions(Color.GREEN, Main.places, "btn_1_1", "btn_2_2", "btn_3_3") || checkPositions(Color.GREEN, Main.places, "btn_1_3", "btn_2_2", "btn_3_1")){
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("You Win!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.GREEN);
            }else{
                Main.state = State.AFTER;
                Text text = (Text) stage.getScene().lookup("#winner_na");
                text.setText("Draw!");
                stage.getScene().lookup("#winner_na").setVisible(true);
                stage.getScene().lookup("#bck").setVisible(true);
                fetchWin(stage, Color.NEUTRAL);
            }
        }
    }

    private void fetchWin(Stage stage, Color winner){
        stage.getScene().setEventDispatcher(null);
        Button back = (Button) stage.getScene().lookup("#bck");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.state = State.MENU;
                new Menu(stage);
            }
        });
    }

    private void launchKI(Stage stage, boolean begin){
        if(difficulty == Difficulty.EASY){
            Easy.choosePlace(stage, Main.freelist, Main.places, begin);
        }else if(difficulty == Difficulty.MEDIUM){
            Medium.choosePlace(stage, Main.freelist, Main.places, begin);
        }else if(difficulty == Difficulty.HARD){
            Hard.choosePlace(stage, Main.freelist, Main.places, begin);
        }else if(difficulty == Difficulty.ULTRA){
            Ultra.choosePlace(stage, Main.places, begin);
        }
    }

    private boolean checkPositions(Color color, HashMap<String, Color> places, String pos1, String pos2, String pos3){
        if(places.get(pos1) == places.get(pos2)){
            if(places.get(pos1) == places.get(pos3)){
                if(places.get(pos2) == places.get(pos3)){
                    if(places.get(pos1) != Color.NEUTRAL && places.get(pos2) != Color.NEUTRAL && places.get(pos3) != Color.NEUTRAL){
                        if(places.get(pos1).equals(color) && places.get(pos2).equals(color) && places.get(pos3).equals(color)){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
