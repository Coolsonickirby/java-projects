/*
 * - File Structure:
 *  - char[4] magic
 *  - uint32 scoreCount
 *  - uint32[scoreCount] scores
 * 
 */

package com.game.Scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;

import com.game.App;
import com.game.Utils;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.SpriteData;
import com.game.Managers.RenderManager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Leaderboard extends Scene {
    public static final String MAGIC = "FBLB";
    public static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;
    public static final String LEADERBOARD_PATH = "leaderboard.flb"; 
    private static ObservableList<Integer> SCORES = null;
    private TableView<Integer> SCORES_TABLE = null;
    private SceneType SCENE_TO_RET = SceneType.LEADERBOARD;

    public Leaderboard(){
        SCORES_TABLE = new TableView();
        SCORES_TABLE.setPrefWidth(App.SCREEN_WIDTH / 3.5);
        SCORES_TABLE.setPrefHeight(App.SCREEN_HEIGHT / 2);
        SCORES_TABLE.setTranslateX((App.SCREEN_WIDTH - SCORES_TABLE.getPrefWidth()) / 2);
        SCORES_TABLE.setTranslateY((App.SCREEN_HEIGHT - SCORES_TABLE.getPrefHeight()) / 2);
        SCORES_TABLE.setUserData(new SpriteData("leaderboard", 1000));

        SCORES_TABLE.setItems(SCORES);

        TableColumn ranking = new TableColumn<>("Ranking");
        // Thanks to James_D on stackoverflow! https://stackoverflow.com/questions/33353014/creating-a-row-index-column-in-javafx
        ranking.setCellFactory(col -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
        
        TableColumn score = new TableColumn<>("Score");
        score.setCellFactory(col -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(SCORES.get(index)));
                }
            }
        });

        SCORES_TABLE.getColumns().addAll(ranking, score);
        
        this.NODES.add(SCORES_TABLE);

        // Add back button
    	Button menuButton = new Button(App.SPRITESHEET);
        menuButton.setXRect(462);
        menuButton.setYRect(26);

    	menuButton.setXSize(40);
    	menuButton.setYSize(14);
    	
    	menuButton.setWidth(menuButton.getXSize() * 2);
    	menuButton.setHeight(menuButton.getYSize() * 2);
    	menuButton.getTransform().XPos = ((App.SCREEN_WIDTH - menuButton.getWidth()) / 2);
    	menuButton.getTransform().YPos = ((App.SCREEN_HEIGHT - menuButton.getHeight())) - RenderManager.GROUND_HEIGHT - 20;

        menuButton.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.MAIN_MENU;
            }
            
            @Override
            public void onHover() {
                // Do Nothing
            }
        });
        
        menuButton.setLayer(999);
    
        this.SPRITES.add(menuButton);
    }

    @Override
    public SceneType Run(){
        this.NODES.forEach(node -> RenderManager.AddNode(node));
        this.SPRITES.forEach(sprite -> sprite.draw());
        return SCENE_TO_RET;
    }

    public static void StaticSetup(){
        SCORES = FXCollections.observableArrayList();;
        ReadLeaderboardFromFile(LEADERBOARD_PATH);
    }

    public static void SaveLeaderboardToFile(String path){
        try {
            SortScores();
            OutputStream outputStream = new FileOutputStream(new File(path));
            Utils.WriteString(outputStream, MAGIC);
            Utils.WriteUInt32(outputStream, SCORES.size(), BYTE_ORDER);
            SCORES.forEach(score -> Utils.WriteUInt32(outputStream, score, BYTE_ORDER));
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("Failed saving leaderboard!");
        }
    }

    public static void ReadLeaderboardFromFile(String path){
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            
            
            if(!Utils.ReadString(inputStream, MAGIC.length()).equals(MAGIC)){ throw new Exception("Leaderboard File MAGIC did not equal set MAGIC!"); }

            int scoreCount = Utils.ReadUInt32(inputStream, BYTE_ORDER);

            for(int i = 0; i < scoreCount; i++){
                SCORES.add(Utils.ReadUInt32(inputStream, BYTE_ORDER));
            }

            SortScores();
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("[Leaderboard::ReadLeaderboardFromFile] Failed reading from leaderboard file!");
        }
    }

    private static void SortScores(){
        Collections.sort(SCORES);
        Collections.reverse(SCORES);
    }

    public static void AddScore(int score){
        if(SCORES == null){ SCORES = FXCollections.observableArrayList();; }
        SCORES.add(score);
        SortScores();
    }
}