package com.game.Scenes;
import com.game.Entities.Sprite;
import java.util.ArrayList;

public class Scene {
    public ArrayList<Sprite> SPRITES = new ArrayList<Sprite>();

    public SceneType Run(){
        return SceneType.MAIN_MENU;
    }

    public void mousePressed(){
        System.out.println("Mouse Pressed!");
    }
}
