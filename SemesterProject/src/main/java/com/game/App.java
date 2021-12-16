/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|                                                                |*
 *| Notes: Flappy Bird is back in action baby! This was my first   |*
 *| time writing a game from "scratch" (not using something like   |*
 *| Unity or Unreal Engine) and it was incredibly fun! There are   |*
 *| a couple of issues with this, such as:                         |*
 *|   - Bird rotation not matching his velocity                    |*
 *|   - Animation not being consistent across different computers  |*
 *|   - Leaderboard and Options being bare bones                   |*
 *|   - No MP3 support (was forced to pick between ogg and mp3)    |*
 *|   - Very little comments (didn't comment much cause I was      |*
 *| super focused on writing this project and getting it to work   |*
 *| lol)                                                           |*
 *|   - Background and Ground don't scroll                         |*
 *|                                                                |*
 *| But aside from those issues, I'm proud of this game clone!     |*
 *| Super proud about myself rn for actually managing to get it    |*
 *| working before the due date lol.                               |*
 *|----------------------------------------------------------------|*
 *| Third-Party Libraries Used:                                    |*
 *| - jLayer (mp3spi and vorbisspi dep)                            |*
 *| - jogg (vorbisspi dep)                                         |*
 *| - jorbis (vorbisspi dep)                                       |*
 *| - tritonus (mp3spi and vorbisspi dep)                          |*
 *| - vorbisspi (ogg support)                                      |*
 *| - mp3spi (mp3 support)                                         |*
 *| - JavaFX (Rendering Graphics + Control)                        |*
 *|----------------------------------------------------------------|*
 *| Credits:                                                       |*
 *|  - Ali Hussain | Programmer                                    |*
 *|  - Superjustinbros | Spritesheet                               |*
 *|  - StackOverFlow | Answering Programming Questions             |*
 *|  - Majora & Catalyst | Beta Testing                            |*
 *|  - Kimberly Moscardelli | For letting me work on these fun     |*
 *| projects and for being a cool professor!                       |*
 *|----------------------------------------------------------------|*
 */

package com.game;

import java.io.File;

import com.game.Audio.MusicPlayer;
import com.game.Audio.SFXPlayer;
import com.game.Managers.RenderManager;
import com.game.Managers.SceneManager;
import com.game.Scenes.Credits;
import com.game.Scenes.Leaderboard;
import com.game.Scenes.Options;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static final boolean IS_DEBUG = false;
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 512;
    public static final String PLAYLIST = "playlist.txt";
    public static Stage MAIN_STAGE = null;
    public static Image SPRITESHEET = new Image(App.class.getResourceAsStream("Resources/spritesheet.png"));
    public static Font FLAPPY_BIRD_FONT = Font.loadFont(App.class.getResourceAsStream("Resources/04B_19__.TTF"), 50);
    public static Font FLAPPY_BIRD_FONT_SMALL = Font.loadFont(App.class.getResourceAsStream("Resources/04B_19__.TTF"), 25);
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
        SFXPlayer.InitalSetup();
        Leaderboard.StaticSetup();
        Credits.StaticSetup();

        if((new File(PLAYLIST).exists())){
            MusicPlayer.InitalSetup();
            MusicPlayer.SetVolume(1);
            if(!MusicPlayer.LoadPlaylist(PLAYLIST)){ System.out.format("Failed loading playlist %s!\n", PLAYLIST); }
            else{ MusicPlayer.PlayRandom(); }
        }

        Options.StaticSetup();

        AnimationTimer GAME_LOOP = new AnimationTimer() {
            @Override
            public void handle(long now){
                // Game loop here
                RenderManager.Clear();
                RenderManager.DrawHUD();
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
