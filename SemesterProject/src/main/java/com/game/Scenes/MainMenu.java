package com.game.Scenes;

import com.game.App;
import com.game.Entities.Pipe;
import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

public class MainMenu extends Scene {
    public MainMenu(){
        // Initialize sprite stuff
    	
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
    	
        Player test = new Player(App.SPRITESHEET);
        test.getTransform().XPos = ((App.SCREEN_WIDTH - test.getWidth()) / 2);
        test.getTransform().YPos = ((App.SCREEN_HEIGHT - test.getHeight()) / 2);
        test.getTransform().XRot = 3;
        test.getTransform().YRot = 4;

        double randomPipePos = 100;
        Pipe pipe = new Pipe(App.SPRITESHEET);
        pipe.getTransform().XPos = App.SCREEN_WIDTH - 50;
        pipe.getTransform().YPos = (App.SCREEN_HEIGHT - RenderManager.GROUND_HEIGHT) - randomPipePos;

        this.SPRITES.add(test);
        this.SPRITES.add(pipe);
    }
    
    @Override
    public SceneType Run(){
        this.SPRITES.forEach(sprite -> sprite.draw());
        this.SPRITES.forEach(sprite -> sprite.update());
        return super.Run();
    }
}
