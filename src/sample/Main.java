package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.map.Map;

public class Main extends Application {

    private Map map;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(Const.WIDTH, Const.HEIGHT);
        BorderPane group = new BorderPane();
        group.setCenter(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Labyrinth");
        primaryStage.show();

        setKeyPressListener(scene);
        setMouseClickListener(scene);

        initMap();
    }

    private void initMap() {
        map = new Map();
    }

    private void setMouseClickListener(Scene scene) {
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

    private void setKeyPressListener(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
