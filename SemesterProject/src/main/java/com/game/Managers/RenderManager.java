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

    public static void Clear(){
        RENDER_PANE.getChildren().removeAll();
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
        background.setStyle("-fx-background-color: #00ff00");
        RENDER_PANE.getChildren().add(background);
    }

    private static double ConvertXYToAngle(double x1, double y1, double x2, double y2){
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        double rad = Math.atan2(deltaY, deltaX); // In radians
        return rad * (180 / Math.PI);
    }
    
    public static void Draw(Sprite sprite){
        ImageView img = new ImageView(sprite.getSpritesheet());
        Rectangle2D rect = new Rectangle2D(sprite.getXRect(), sprite.getYRect(), sprite.getXSize(), sprite.getYSize());
        img.setSmooth(false);
        img.setViewport(rect);
        img.setFitWidth(sprite.getWidth());
        img.setFitHeight(sprite.getHeight());
        img.relocate(sprite.getTransform().XPos, sprite.getTransform().YPos);
        
        
        
        // Doesn't Work
        img.getTransforms().add(new Rotate(ConvertXYToAngle(
            sprite.getTransform().XRot, sprite.getTransform().YRot, sprite.getTransform().XRot, sprite.getTransform().YRot
        )));

        img.setOnMousePressed(event -> {
            sprite.onClick();
        });

        RENDER_PANE.getChildren().add(img);
    }
}
