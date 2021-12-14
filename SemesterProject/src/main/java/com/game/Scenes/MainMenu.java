package com.game.Scenes;

import com.game.App;
import com.game.Entities.Sprite;

public class MainMenu extends Scene {
    private SceneType SCENE_TO_RET = SceneType.MAIN_MENU;

    public MainMenu(){
        // Initialize sprite stuff    	
        // Game Name from spritesheet
    	Sprite title = new Sprite(App.SPRITESHEET);
    	title.setXRect(351);
    	title.setYRect(91);
    	
    	title.setXSize(89);
    	title.setYSize(24);
    	
    	title.setWidth(title.getXSize() * 3);
    	title.setHeight(title.getYSize() * 3);
    	title.getTransform().XPos = ((App.SCREEN_WIDTH - title.getWidth()) / 2);
    	title.getTransform().YPos = ((App.SCREEN_HEIGHT - title.getHeight()) / 2) - 200;
    	this.SPRITES.add(title);
    }

    @Override
    public SceneType Run(){
        SPRITES.forEach(sprite -> sprite.draw());
        SPRITES.forEach(sprite -> sprite.update());
        return SCENE_TO_RET;
    }

    @Override
    public void mousePressed(){
        SCENE_TO_RET = SceneType.GAME;
    }
}
