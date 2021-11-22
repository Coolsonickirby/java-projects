package com.game.Managers;
import com.game.Entities.Sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RenderManager {
    public static Pane RENDER_PANE = new Pane();

    public static void Clear(){
        RENDER_PANE.getChildren().removeAll();
    }
    
    public static void Draw(Sprite sprite){
        ImageView img = new ImageView(sprite.getSpritesheet());
        Rectangle2D rect = new Rectangle2D(sprite.getXRect(), sprite.getYRect(), sprite.getWidth(), sprite.getHeight());
        img.setSmooth(false);
        img.setViewport(rect);
        img.relocate(sprite.getX(), sprite.getY());
        img.setFitWidth(sprite.getXSize());
        img.setFitHeight(sprite.getYSize());
        RENDER_PANE.getChildren().add(img);
    }
}
