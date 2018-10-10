package KIClasses;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Color;
import sample.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ultra {

    public static ArrayList<String[]> miles = new ArrayList<>();
    public static String[] current;

    public static void intialize(){
        miles.clear();
        current = null;

        String[] m1 = {"btn_1_1", "btn_2_2", "btn_1_3"};
        miles.add(m1);
        String[] m2 = {"btn_1_1", "btn_2_2", "btn_3_1"};
        miles.add(m2);
        String[] m3 = {"btn_1_3", "btn_2_2", "btn_3_3"};
        miles.add(m3);
        String[] m4 = {"btn_3_1", "btn_2_2", "btn_3_3"};
        miles.add(m4);
        String[] m5 = {"btn_1_1", "btn_1_3", "btn_3_3"};
        miles.add(m5);
        String[] m6 = {"btn_1_1", "btn_3_1", "btn_3_3"};
        miles.add(m6);
        String[] m7 = {"btn_1_1", "btn_1_2", "btn_2_1"};
        miles.add(m7);
        String[] m8 = {"btn_1_2", "btn_1_3", "btn_2_3"};
        miles.add(m8);
        String[] m9 = {"btn_2_3", "btn_3_2", "btn_3_3"};
        miles.add(m9);
        String[] m10 = {"btn_2_1", "btn_3_1", "btn_3_2"};
        miles.add(m10);
        String[] m11 = {"btn_1_2", "btn_2_1", "btn_2_2"};
        miles.add(m11);
        String[] m12 = {"btn_3_2", "btn_2_1", "btn_2_2"};
        miles.add(m12);
        String[] m13 = {"btn_3_2", "btn_2_3", "btn_2_2"};
        miles.add(m13);

    }

    public static void choosePlace(Stage stage, HashMap<String, Color> places, boolean begin){
        if(!begin){
            String kiDanger = dangerPosition(Color.RED, places);
            if(kiDanger != null) {
                Button button = (Button) stage.getScene().lookup("#" + kiDanger);
                button.setStyle("-fx-background-color: #f82121");
                button.setOpacity(0.5);

                Main.places.put(kiDanger, Color.RED);
                Main.freelist.remove(kiDanger);

                Main.waiting = false;
            }else{
                String PlayerDanger = dangerPosition(Color.GREEN, places);
                if(PlayerDanger != null){
                    Button button = (Button) stage.getScene().lookup("#" + PlayerDanger);
                    button.setStyle("-fx-background-color: #f82121");
                    button.setOpacity(0.5);

                    Main.places.put(PlayerDanger, Color.RED);
                    Main.freelist.remove(PlayerDanger);

                    Main.waiting = false;
                }else{
                    if(Main.freelist.size() > 1){
                        placeMile(places, stage);
                    }else{
                        setRandom(stage);
                    }
                }
            }
        }else{
            placeMile(places, stage);
        }
    }

    public static void placeMile(HashMap<String, Color> places, Stage stage){
        if(current != null){
            boolean error = fetchMile(places, stage);
            if(error){
                miles.remove(current);
                if(miles.size() > 0){
                    if(miles.size() > 1){
                        current = miles.get(new Random().nextInt(miles.size()));
                        for(int i = 0; i < 3 && error && miles.size() > 0; i++){
                            miles.remove(current);
                            if(miles.size() > 1){
                                current = miles.get(new Random().nextInt(miles.size()));
                                error = fetchMile(places, stage);
                            }else{
                                current = miles.get(0);
                                error = fetchMile(places, stage);
                            }
                        }
                        if(error || miles.size() == 0){
                            setRandom(stage);
                        }
                    }else{
                        current = miles.get(0);
                        if(fetchMile(places, stage)) {
                            setRandom(stage);
                        }
                    }
                }else{
                    setRandom(stage);
                }
            }
        }else{
            if(miles.size() > 0){
                if(miles.size() > 1){
                    current = miles.get(new Random().nextInt(miles.size()));
                    boolean error = fetchMile(places, stage);
                    for(int i = 0; i < 4 && error && miles.size() > 0; i++){
                        miles.remove(current);
                        if(miles.size() > 1){
                            current = miles.get(new Random().nextInt(miles.size()));
                            error = fetchMile(places, stage);
                        }else{
                            current = miles.get(0);
                            error = fetchMile(places, stage);
                        }
                    }
                    if(error || miles.size() == 0){
                        setRandom(stage);
                    }
                }else{
                    current = miles.get(0);
                    if(fetchMile(places, stage)) {
                        setRandom(stage);
                    }
                }
            }else{
                setRandom(stage);
            }
        }

    }

    private static void setRandom(Stage stage){
        if (Main.freelist.size() > 0) {
            if (Main.freelist.size() > 1) {
                int rnd = new Random().nextInt(Main.freelist.size());
                String string = Main.freelist.get(rnd);
                Button button = (Button) stage.getScene().lookup("#" + string);
                button.setStyle("-fx-background-color: #f82121");
                button.setOpacity(0.5);

                Main.places.put(string, Color.RED);
                Main.freelist.remove(string);

                Main.waiting = false;
            } else {
                String string = Main.freelist.get(0);
                Button button = (Button) stage.getScene().lookup("#" + string);
                button.setStyle("-fx-background-color: #f82121");
                button.setOpacity(0.5);

                Main.places.put(string, Color.RED);
                Main.freelist.remove(string);

                Main.waiting = false;
            }
        }
    }

    private static boolean fetchMile(HashMap<String, Color> places, Stage stage){
        boolean error = false;
        boolean placed = false;
        for(int i = 0; i < current.length && !error && !placed; i++){
            if(places.get(current[i]) == Color.RED){
                error = true;
            }else if(places.get(current[i]) == Color.NEUTRAL){
                placed = true;

                Button button = (Button) stage.getScene().lookup("#" + current[i]);
                button.setStyle("-fx-background-color: #f82121");
                button.setOpacity(0.5);

                Main.places.put(current[i], Color.RED);
                Main.freelist.remove(current[i]);

                Main.waiting = false;
            }
        }
        return error;
    }

    private static ArrayList<String> fetchPositions(Color color, HashMap<String, Color> places){
        ArrayList<String> list = new ArrayList<>();

        for(String st : places.keySet()){
            if(places.get(st).equals(color)){
                if(!list.contains(st))
                list.add(st);
            }
        }

        return list;
    }

    private static String dangerPosition(Color color, HashMap<String, Color> places){
        if(checkPositions(color, places, "btn_1_1", "btn_1_2", "btn_1_3") != null){
            return checkPositions(color, places, "btn_1_1", "btn_1_2", "btn_1_3");
        }else if(checkPositions(color, places, "btn_2_1", "btn_2_2", "btn_2_3") != null){
            return checkPositions(color, places, "btn_2_1", "btn_2_2", "btn_2_3");
        }else if(checkPositions(color, places, "btn_3_1", "btn_3_2", "btn_3_3") != null){
            return checkPositions(color, places, "btn_3_1", "btn_3_2", "btn_3_3");
        }else if(checkPositions(color, places, "btn_1_1", "btn_2_1", "btn_3_1") != null){
            return checkPositions(color, places, "btn_1_1", "btn_2_1", "btn_3_1");
        }else if(checkPositions(color, places, "btn_1_2", "btn_2_2", "btn_3_2") != null){
            return checkPositions(color, places, "btn_1_2", "btn_2_2", "btn_3_2");
        }else if(checkPositions(color, places, "btn_1_3", "btn_2_3", "btn_3_3") != null){
            return checkPositions(color, places, "btn_1_3", "btn_2_3", "btn_3_3");
        }else if(checkPositions(color, places, "btn_1_1", "btn_2_2", "btn_3_3") != null){
            return checkPositions(color, places, "btn_1_1", "btn_2_2", "btn_3_3");
        }else if(checkPositions(color, places, "btn_1_3", "btn_2_2", "btn_3_1") != null){
            return checkPositions(color, places, "btn_1_3", "btn_2_2", "btn_3_1");
        }else{
            return null;
        }
    }

    private static String checkPositions(Color color, HashMap<String, Color> places, String pos1, String pos2, String pos3){
        if(places.get(pos1) == places.get(pos2)){
            if(places.get(pos1) == color){
                if(places.get(pos1) != places.get(pos3)){
                    if(places.get(pos3) == Color.NEUTRAL){
                        return pos3;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else if(places.get(pos1) == places.get(pos3)){
            if(places.get(pos1) == color){
                if(places.get(pos1) != places.get(pos2)){
                    if(places.get(pos2) == Color.NEUTRAL){
                        return pos2;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else if(places.get(pos2) == places.get(pos3)){
            if(places.get(pos2) == color){
                if(places.get(pos2) != places.get(pos1)){
                    if(places.get(pos1) == Color.NEUTRAL){
                        return pos1;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

}
