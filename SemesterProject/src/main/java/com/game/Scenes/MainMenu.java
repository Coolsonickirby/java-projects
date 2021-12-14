package com.game.Scenes;

import com.game.App;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

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

        // Play Button
    	Button playBtn = new Button(App.SPRITESHEET);
    	playBtn.setXRect(354);
    	playBtn.setYRect(118);
    	
    	playBtn.setXSize(52);
    	playBtn.setYSize(29);
    	
    	playBtn.setWidth(playBtn.getXSize() * 3);
    	playBtn.setHeight(playBtn.getYSize() * 2.5);
    	playBtn.getTransform().XPos = ((App.SCREEN_WIDTH - playBtn.getWidth()) / 2);
    	playBtn.getTransform().YPos = ((App.SCREEN_HEIGHT - playBtn.getHeight())) - RenderManager.GROUND_HEIGHT - 20;
    	
        playBtn.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.GAME;
            }
            
            @Override
            public void onHover() {
                System.out.println("Button Hover!");                
            }
        });
        this.SPRITES.add(playBtn);

        // Demo Birb
        Player demoBird = new Player(App.SPRITESHEET);
        demoBird.setIsDemo(true);
        this.SPRITES.add(demoBird);
    }

    @Override
    public SceneType Run(){
        SPRITES.forEach(sprite -> sprite.draw());
        SPRITES.forEach(sprite -> sprite.update());
        return SCENE_TO_RET;
    }
}
