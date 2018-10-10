package KIClasses;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Color;
import sample.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Easy {

    public static void choosePlace(Stage stage, ArrayList<String> freelist, HashMap<String, Color> places, boolean begin){
        if(!begin){
            if(new Random().nextInt(20) > 10){
                String PlayerDanger = dangerPosition(Color.GREEN, places);
                if(PlayerDanger != null){
                    Button button = (Button) stage.getScene().lookup("#" + PlayerDanger);
                    button.setStyle("-fx-background-color: #f82121");
                    button.setOpacity(0.5);

                    Main.places.put(PlayerDanger, Color.RED);
                    Main.freelist.remove(PlayerDanger);

                    Main.waiting = false;
                }else{
                    String kiDanger = dangerPosition(Color.RED, places);
                    if(kiDanger != null) {
                        Button button = (Button) stage.getScene().lookup("#" + kiDanger);
                        button.setStyle("-fx-background-color: #f82121");
                        button.setOpacity(0.5);

                        Main.places.put(kiDanger, Color.RED);
                        Main.freelist.remove(kiDanger);

                        Main.waiting = false;
                    }else{
                        if(Main.freelist.size() > 0){
                            if(Main.freelist.size() > 1){
                                int rnd = new Random().nextInt(freelist.size());
                                String string = freelist.get(rnd);
                                Button button = (Button) stage.getScene().lookup("#" + string);
                                button.setStyle("-fx-background-color: #f82121");
                                button.setOpacity(0.5);

                                Main.places.put(string, Color.RED);
                                Main.freelist.remove(string);

                                Main.waiting = false;
                            }else{
                                String string = freelist.get(0);
                                Button button = (Button) stage.getScene().lookup("#" + string);
                                button.setStyle("-fx-background-color: #f82121");
                                button.setOpacity(0.5);

                                Main.places.put(string, Color.RED);
                                Main.freelist.remove(string);

                                Main.waiting = false;
                            }
                        }
                    }
                }
            }else{
                if(Main.freelist.size() > 0){
                    if(Main.freelist.size() > 1){
                        int rnd = new Random().nextInt(freelist.size());
                        String string = freelist.get(rnd);
                        Button button = (Button) stage.getScene().lookup("#" + string);
                        button.setStyle("-fx-background-color: #f82121");
                        button.setOpacity(0.5);

                        Main.places.put(string, Color.RED);
                        Main.freelist.remove(string);

                        Main.waiting = false;
                    }else{
                        String string = freelist.get(0);
                        Button button = (Button) stage.getScene().lookup("#" + string);
                        button.setStyle("-fx-background-color: #f82121");
                        button.setOpacity(0.5);

                        Main.places.put(string, Color.RED);
                        Main.freelist.remove(string);

                        Main.waiting = false;
                    }
                }
            }
        }else{
            if(Main.freelist.size() > 0){
                if(Main.freelist.size() > 1){
                    int rnd = new Random().nextInt(freelist.size());
                    String string = freelist.get(rnd);
                    Button button = (Button) stage.getScene().lookup("#" + string);
                    button.setStyle("-fx-background-color: #f82121");
                    button.setOpacity(0.5);

                    Main.places.put(string, Color.RED);
                    Main.freelist.remove(string);

                    Main.waiting = false;
                }else{
                    String string = freelist.get(0);
                    Button button = (Button) stage.getScene().lookup("#" + string);
                    button.setStyle("-fx-background-color: #f82121");
                    button.setOpacity(0.5);

                    Main.places.put(string, Color.RED);
                    Main.freelist.remove(string);

                    Main.waiting = false;
                }
            }
        }
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
