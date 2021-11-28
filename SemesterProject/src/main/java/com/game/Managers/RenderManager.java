package com.game.Managers;
import com.game.Entities.Sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Group;

public class RenderManager {
    public static Pane RENDER_PANE = new Pane();
    public static int ROTATE_DEGREE = 0;

    public static void Clear(){
        RENDER_PANE.getChildren().removeAll();
    }
    
    public static void drawHUD(){
      Group HUD = new Group();
      Text t = new Text();
      t.setText("Now Playing: Surge of the Crimson Shouts (Possessed Klug's Theme) - Puyo Puyo Tetris 2");
      HUD.setTranslateX(5);
      HUD.setTranslateY(10);
      
      HUD.getChildren().add(t);
      
      RENDER_PANE.getChildren().add(HUD);
    }
    
    public static void Draw(Sprite sprite){
        ImageView img = new ImageView(sprite.getSpritesheet());
        Rectangle2D rect = new Rectangle2D(sprite.getXRect(), sprite.getYRect(), sprite.getWidth(), sprite.getHeight());
        img.setSmooth(false);
        img.setViewport(rect);
        img.relocate(sprite.getX(), sprite.getY());
        img.setFitWidth(sprite.getWidth());
        img.setFitHeight(sprite.getHeight());
        RENDER_PANE.getChildren().add(img);
    }
}
