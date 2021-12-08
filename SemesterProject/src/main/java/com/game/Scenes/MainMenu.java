package com.game.Scenes;

import com.game.App;
import com.game.Entities.Player;

public class MainMenu extends Scene {
    public MainMenu(){
        // Initalize sprite stuff
        Player test = new Player(App.SPRITESHEET);
        test.setLayer(0);
        test.setXRect(3);
        test.setYRect(491);
        test.setXSize(17);
        test.setYSize(12);
        test.setWidth(17 * 2);
        test.setHeight(12 * 2);
        test.getTransform().XPos = App.SCREEN_WIDTH / 2;
        test.getTransform().YPos = App.SCREEN_HEIGHT / 2;
        test.getTransform().XRot = 20;
        test.getTransform().YRot = 20;
        this.SPRITES.add(test);
    }
    
    @Override
    public SceneType Run(){
        this.SPRITES.forEach(sprite -> sprite.draw());
        this.SPRITES.forEach(sprite -> sprite.update());
        return super.Run();
    }
}
