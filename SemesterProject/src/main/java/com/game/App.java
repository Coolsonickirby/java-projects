package com.game;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.game.Managers.RenderManager;
import com.game.Scenes.MainMenu;
import com.game.Scenes.SceneType;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 512;
    public static Stage MAIN_STAGE = null;
    public static Image SPRITESHEET = new Image(App.class.getResourceAsStream("Resources/spritesheet.png"));

    public static HashMap<SceneType, com.game.Scenes.Scene> SCENES = new HashMap<SceneType, com.game.Scenes.Scene>();
    public static SceneType CURRENT_SCENE = SceneType.MAIN_MENU;
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

        SCENES.put(SceneType.MAIN_MENU, new MainMenu());
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {}

        FPS.calcBeginTime();

        AnimationTimer GAME_LOOP = new AnimationTimer() {
            @Override
            public void handle(long now){
                // Game loop here
                RenderManager.Clear();
                RenderManager.DrawBackground();
                CURRENT_SCENE = SCENES.get(CURRENT_SCENE).Run();
                FPS.calcDeltaTime();
            }
        };

        GAME_LOOP.start();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
