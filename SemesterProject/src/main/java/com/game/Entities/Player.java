// Physics copied from here --- https://gamedev.stackexchange.com/questions/70268/can-someone-explain-flappy-birds-physics-to-me
package com.game.Entities;
import com.game.FPS;
import javafx.scene.image.Image;

public class Player extends Sprite {
    private double vSpeed = 0.0;
    private double jSpeed = 35;
    private double fallingSpeed = -100;
    private boolean IS_DEAD = false;

    public Player(Image spritesheet){
        super(spritesheet);
    }
    
    public boolean getIsDead() { return IS_DEAD; }
    public void setIsDead(boolean IS_DEAD) { this.IS_DEAD = IS_DEAD; }

    @Override
    public void onClick(){
        System.out.println("Player clicked!");
        if(!IS_DEAD) {
        	vSpeed = jSpeed;
        }
    }

    @Override
    public void update(){
        // Player physics go here
    	if(!IS_DEAD) {
    		this.getTransform().YPos -= (vSpeed * FPS.getDeltaTime());
            vSpeed += fallingSpeed * FPS.getDeltaTime();
    	}
    }
}
