// Physics copied from here --- https://gamedev.stackexchange.com/questions/70268/can-someone-explain-flappy-birds-physics-to-me
package com.game.Entities;
import com.game.App;
import com.game.FPS;
import com.game.Managers.RenderManager;

import javafx.scene.image.Image;

public class Player extends Sprite {
    private double vSpeed = 0.0;
    private double jSpeed = 55;
    private double fallingSpeed = -100;
    private boolean IS_DEAD = false;
    private int currentKeyFrame = 0;
    private int currentAnimationFrame = 0;
    private int[][] ANIMATION = new int[][] {
        new int[] { 0, 3, 491 },
        new int[] { 15, 31, 491 },
        new int[] { 30, 59, 491 },
        new int[] { 45, 31, 491 },
        new int[] { 60, 3, 491 },
    };

    public Player(Image spritesheet){
        super(spritesheet);
        this.setLayer(0);
        this.setXRect(3);
        this.setYRect(491);
        this.setXSize(17);
        this.setYSize(12);
        this.setWidth(this.getXSize() * 2);
        this.setHeight(this.getYSize() * 2);
    }
    
    public boolean getIsDead() { return IS_DEAD; }
    public void setIsDead(boolean IS_DEAD) { this.IS_DEAD = IS_DEAD; }

    @Override
    public void onClick(){
        System.out.println("Player clicked!");
        IS_DEAD = false;
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
            System.out.println(currentKeyFrame);
            if(currentKeyFrame >= ANIMATION[currentAnimationFrame][0]){
                this.setXRect(ANIMATION[currentAnimationFrame][1]);
                this.setYRect(ANIMATION[currentAnimationFrame][2]);
                currentAnimationFrame++;
                if(currentAnimationFrame >= ANIMATION.length){
                    currentAnimationFrame = 0;
                    currentKeyFrame = 0;
                }
            }
            currentKeyFrame++;
    	}
        
        if(this.getTransform().YPos >= ((App.SCREEN_HEIGHT - RenderManager.GROUND_HEIGHT) - this.getHeight())){
            IS_DEAD = true;
        }

    }
}
