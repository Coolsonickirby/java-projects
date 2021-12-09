package com.game.Managers;
import com.game.App;
import com.game.Entities.Sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;

public class RenderManager {
    public static Pane RENDER_PANE = new Pane();
    public static int ROTATE_DEGREE = 0;
    public static double GROUND_HEIGHT = 75.0;

    public static void Clear(){
        RENDER_PANE.getChildren().clear();
    }
    
    public static void DrawHUD(){
        Group HUD = new Group();
        Text t = new Text();
        t.setText("Now Playing: Surge of the Crimson Shouts (Possessed Klug's Theme) - Puyo Puyo Tetris 2");
        HUD.setTranslateX(5);
        HUD.setTranslateY(10);
        
        HUD.getChildren().add(t);
        
        RENDER_PANE.getChildren().add(HUD);
    }

    public static void DrawBackground(){
        Pane background = new Pane();
        background.setPrefSize(App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
        background.setStyle("-fx-background-color: #a0c0ff");
        background.setTranslateZ(-10);
        
        Pane ground = new Pane();
        ground.setPrefSize(App.SCREEN_WIDTH, GROUND_HEIGHT);
        ground.setTranslateY(App.SCREEN_HEIGHT - GROUND_HEIGHT);
        ground.setStyle("-fx-background-color: #ff0000");
        ground.setTranslateZ(0);

        RENDER_PANE.getChildren().addAll(background, ground);
    }
    
    public static void Draw(Sprite sprite){
        ImageView img = sprite.getSpritesheet();
        Rectangle2D rect = new Rectangle2D(sprite.getXRect(), sprite.getYRect(), sprite.getXSize(), sprite.getYSize());
        img.setSmooth(false);
        img.setViewport(rect);
        img.setFitWidth(sprite.getWidth());
        img.setFitHeight(sprite.getHeight());
        img.relocate(sprite.getTransform().XPos, sprite.getTransform().YPos);
        img.setTranslateZ(sprite.getLayer());
        
        // Add rotation support

        img.setOnMousePressed(event -> {
            sprite.onClick();
        });

        RENDER_PANE.getChildren().add(img);
    }
}
