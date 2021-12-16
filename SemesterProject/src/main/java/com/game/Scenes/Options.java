/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Scenes;

import java.io.OutputStream;

import com.game.App;
import com.game.Audio.MusicPlayer;
import com.game.Audio.SFXPlayer;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.Sprite;
import com.game.Entities.SpriteData;
import com.game.Managers.RenderManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import com.game.App;
import com.game.Utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Options extends Scene {
    private static final String MAGIC = "FBOP";
    private static final String OPTIONS_PATH = "options.fop";
    public static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;
    private SceneType SCENE_TO_RET = SceneType.OPTIONS;
    private static float MUSIC_VOLUME = 1f;
    private static float SFX_VOLUME = 1f;

    
    public static void StaticSetup(){
        ReadOptionsFromFile(OPTIONS_PATH);
        MusicPlayer.SetVolume(MUSIC_VOLUME);
        SFXPlayer.SetVolume(SFX_VOLUME);
    }

    public static void SaveOptionsToFile(String path){
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            Utils.WriteString(outputStream, MAGIC);
            Utils.WriteFloat(outputStream, MUSIC_VOLUME, BYTE_ORDER);
            Utils.WriteFloat(outputStream, SFX_VOLUME, BYTE_ORDER);
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("[Options::SaveOptionsToFile] Failed saving options!");
        }
    }

    public static void ReadOptionsFromFile(String path){
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            if(!Utils.ReadString(inputStream, MAGIC.length()).equals(MAGIC)){ throw new Exception("Options File MAGIC did not equal set MAGIC!"); }
            MUSIC_VOLUME = Utils.ReadFloat(inputStream, BYTE_ORDER);
            SFX_VOLUME = Utils.ReadFloat(inputStream, BYTE_ORDER);
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("[Options::ReadOptionsFromFile] Failed reading from options file!");
        }
    }

    public Options() {
        // Setup options stuff

        Sprite title = new Sprite(App.SPRITESHEET);
        title.setXRect(171);
        title.setYRect(291);

        title.setXSize(125);
        title.setYSize(45);

        title.setWidth(title.getXSize() * 1.5);
        title.setHeight(title.getYSize() * 1.5);
        title.getTransform().XPos = ((App.SCREEN_WIDTH - title.getWidth()) / 2);
        title.getTransform().YPos = ((App.SCREEN_HEIGHT - title.getHeight()) / 2) - 200;

        this.SPRITES.add(title);

        // Add back button
        Button menuButton = new Button(App.SPRITESHEET);
        menuButton.setXRect(462);
        menuButton.setYRect(26);

        menuButton.setXSize(40);
        menuButton.setYSize(14);

        menuButton.setWidth(menuButton.getXSize() * 2);
        menuButton.setHeight(menuButton.getYSize() * 2);
        menuButton.getTransform().XPos = ((App.SCREEN_WIDTH - menuButton.getWidth()) / 2);
        menuButton.getTransform().YPos = ((App.SCREEN_HEIGHT - menuButton.getHeight())) - RenderManager.GROUND_HEIGHT
                - 20;

        menuButton.setAction(new Action() {
            @Override
            public void onClick() {
                SCENE_TO_RET = SceneType.MAIN_MENU;
            }

            @Override
            public void onHover() {
                // Do Nothing
            }
        });

        menuButton.setLayer(999);
        this.SPRITES.add(menuButton);

        VBox optionsHolder = new VBox();
        optionsHolder.setPadding(new Insets(10, 10, 10, 10));
        optionsHolder.setSpacing(5);

        optionsHolder.getChildren().add(GenerateSliderCombo("Music Volume: ", MusicPlayer.GetVolume(), new ChangeListener<Number>() {
            // Thanks to http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                MUSIC_VOLUME = new_val.floatValue();
                MusicPlayer.SetVolume(MUSIC_VOLUME);
                SaveOptionsToFile(OPTIONS_PATH);
            }
        }));

        optionsHolder.getChildren().add(GenerateSliderCombo("SFX Volume: ", SFXPlayer.GetVolume(), new ChangeListener<Number>() {
            // Thanks to http://www.java2s.com/Code/Java/JavaFX/Slidervaluepropertychangelistener.htm
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                SFX_VOLUME = new_val.floatValue();
                SFXPlayer.SetVolume(SFX_VOLUME);
                SaveOptionsToFile(OPTIONS_PATH);
            }
        }));

        optionsHolder.setPrefWidth(App.SCREEN_WIDTH / 3.5);
        optionsHolder.setPrefHeight(App.SCREEN_HEIGHT / 2);
        optionsHolder.setTranslateX((App.SCREEN_WIDTH - optionsHolder.getPrefWidth()) / 2);
        optionsHolder.setTranslateY((App.SCREEN_HEIGHT - optionsHolder.getPrefHeight()) / 2);

        optionsHolder.setUserData(new SpriteData("options", 1000));
        this.NODES.add(optionsHolder);
    }

    private FlowPane GenerateSliderCombo(String label, Float defaultValue, ChangeListener changeEvent){
        FlowPane option = new FlowPane();
        option.setHgap(10);
        option.setVgap(10);
        option.setOrientation(Orientation.HORIZONTAL);

        Label lblDisp = new Label(label);
        Slider slider = new Slider(0, 1, defaultValue);

        slider.valueProperty().addListener(changeEvent);

        option.getChildren().addAll(lblDisp, slider);
        return option;
    }

    @Override
    public SceneType Run() {
        this.SPRITES.forEach(sprite -> sprite.draw());
        this.NODES.forEach(node -> RenderManager.AddNode(node));
        return SCENE_TO_RET;
    }
}
