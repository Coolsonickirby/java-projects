package com.game;

import java.util.ArrayList;

import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 512;
    public static Stage MAIN_STAGE = null;
    public static ArrayList<Sprite> SPRITES = new ArrayList<Sprite>();
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        MAIN_STAGE = primaryStage;
        MAIN_STAGE.setTitle("Mario Party FX");
        MAIN_STAGE.setResizable(false);
        Scene scene = new Scene(RenderManager.RENDER_PANE, SCREEN_WIDTH, SCREEN_HEIGHT);
        MAIN_STAGE.setScene(scene);
        MAIN_STAGE.show();

        SPRITES.add(new Player(new Image(App.class.getResourceAsStream("res/mario.png"))));
        SPRITES.get(0).setHeight(16);
        SPRITES.get(0).setWidth(16);
        SPRITES.get(0).setXRect(0);
        SPRITES.get(0).setYRect(8);
        SPRITES.get(0).setXSize(100);
        SPRITES.get(0).setYSize(100);

        AnimationTimer GAME_LOOP = new AnimationTimer() {
            @Override
            public void handle(long now){
                // Game loop here
                SPRITES.forEach( sprite -> sprite.draw() );
            }
        };

        GAME_LOOP.start();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
