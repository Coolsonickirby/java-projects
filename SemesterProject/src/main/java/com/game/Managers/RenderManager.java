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
    private static Sprite BACKGROUND = new Sprite(App.SPRITESHEET);
    private static Sprite GROUND = new Sprite(App.SPRITESHEET);

    public static void Setup(){
        RENDER_PANE.setOnMousePressed(event -> {
            InputManager.MousePressed();
        });

        BACKGROUND.setXRect(0);
        BACKGROUND.setYRect(0);
        BACKGROUND.setXSize(144);
        BACKGROUND.setYSize(256);
        BACKGROUND.setWidth(App.SCREEN_WIDTH);
        BACKGROUND.setHeight(App.SCREEN_HEIGHT - GROUND_HEIGHT);

        GROUND.setXRect(292);
        GROUND.setYRect(0);
        GROUND.setXSize(168);
        GROUND.setYSize(56);
        GROUND.setWidth(App.SCREEN_WIDTH);
        GROUND.setHeight(GROUND_HEIGHT);
        GROUND.getTransform().YPos = App.SCREEN_HEIGHT - GROUND_HEIGHT;

    }

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
        BACKGROUND.draw();
        GROUND.draw();
    }
    
    public static void Draw(Sprite sprite){
        ImageView img = sprite.getSpritesheet();
        Rectangle2D rect = new Rectangle2D(sprite.getXRect(), sprite.getYRect(), sprite.getXSize(), sprite.getYSize());
        img.setSmooth(false);
        img.setViewport(rect);
        img.setFitWidth(sprite.getWidth());
        img.setFitHeight(sprite.getHeight());
        img.setTranslateX(sprite.getTransform().XPos);
        img.setTranslateY(sprite.getTransform().YPos);
        img.setTranslateZ(sprite.getLayer());
        img.setScaleX(sprite.getTransform().XScale);
        img.setScaleY(sprite.getTransform().YScale);
        img.setScaleZ(sprite.getTransform().ZScale);
        img.setUserData(sprite.getTag());
        
        // Add rotation support
        img.setRotate(sprite.getTransform().XRot);

        img.setOnMousePressed(event -> {
            sprite.onClick();
        });

        RENDER_PANE.getChildren().add(img);
    }
}
