package com.game;

import com.game.Managers.RenderManager;
import com.game.Managers.SceneManager;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static final boolean IS_DEBUG = true;
    public static final int SCREEN_WIDTH = 512;
    public static final int SCREEN_HEIGHT = 500;
    public static Stage MAIN_STAGE = null;
    public static Image SPRITESHEET = new Image(App.class.getResourceAsStream("Resources/spritesheet.png"));
    public static Font FLAPPY_BIRD_FONT = Font.loadFont(App.class.getResourceAsStream("Resources/04B_19__.TTF"), 50);
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        MAIN_STAGE = primaryStage;
        MAIN_STAGE.setTitle("Flappy Bird FX");
        MAIN_STAGE.setResizable(false);
        Scene scene = new Scene(RenderManager.RENDER_PANE, SCREEN_WIDTH, SCREEN_HEIGHT);
        MAIN_STAGE.setScene(scene);
        MAIN_STAGE.show();
        
        FPS.calcBeginTime();
        RenderManager.Setup();
        SceneManager.Setup();

        AnimationTimer GAME_LOOP = new AnimationTimer() {
            @Override
            public void handle(long now){
                // Game loop here
                RenderManager.Clear();
                RenderManager.DrawBackground();
                SceneManager.Run();
                RenderManager.UpdateLayers();
                FPS.calcDeltaTime();
            }
        };

        GAME_LOOP.start();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
