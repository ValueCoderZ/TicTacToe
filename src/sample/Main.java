package sample;

import SceneManagers.Menu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static boolean waiting = false;
    public static int loading = 0;
    public static HashMap<String, Color> places = new HashMap<>();

    public static ArrayList<String> freelist = new ArrayList<>();

    public static Stage stage;
    public static Thread thread;

    public static State state = State.MENU;
    public static MediaPlayer player;


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        stage.setTitle("TicTacToe | Patch 1");

        new Menu(primaryStage);

        stage.initStyle(StageStyle.UNIFIED);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(player != null){
                    player.stop();
                }
            }
        });
        stage.setResizable(false);
        stage.show();
    }

    public static void playSoundtrack(String sound){

        /*
        if(Main.player != null){
            Main.player.stop();
        }

        Media media = new Media(Main.class.getResource("../Sounds/"+sound+".mp3").toString());
        player = new MediaPlayer(media);
        player.setCycleCount(Integer.MAX_VALUE);
        player.setAutoPlay(true);

        player.setVolume(0.025);
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
