// Physics copied from here --- https://gamedev.stackexchange.com/questions/70268/can-someone-explain-flappy-birds-physics-to-me
package com.game.Entities;
import com.game.App;
import com.game.FPS;
import com.game.Managers.RenderManager;

import javafx.scene.image.Image;

public class Player extends Sprite {
    public static final String TAG = "player";
    private double vSpeed = 0.0;
    private double jSpeed = 100;
    private double fallingSpeed = -200;
    private boolean IS_DEAD = false;
    private double currentKeyFrame = 0;
    private int currentAnimationFrame = 0;
    private double GROUND_HEIGHT = 0;
    private int[][] ANIMATION = new int[][] {
        new int[] { 0, 3, 491 },
        new int[] { 20, 31, 491 },
        new int[] { 35, 59, 491 },
        new int[] { 50, 31, 491 },
        new int[] { 65, 3, 491 },
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
        this.setTag(Player.TAG);
        this.GROUND_HEIGHT = ((App.SCREEN_HEIGHT - RenderManager.GROUND_HEIGHT) - this.getHeight());
    }
    
    public boolean getIsDead() { return IS_DEAD; }
    public void setIsDead(boolean IS_DEAD) { this.IS_DEAD = IS_DEAD; }

    @Override
    public void onClick(){
        System.out.println("Player clicked!");
        jump();
    }

    public void jump(){
        if(!IS_DEAD) {
        	vSpeed = jSpeed;
            this.getTransform().XRot = -20;
        }
    }

    public void animation(){
        if(IS_DEAD){ return; }
        if(currentKeyFrame >= ANIMATION[currentAnimationFrame][0]){
            this.setXRect(ANIMATION[currentAnimationFrame][1]);
            this.setYRect(ANIMATION[currentAnimationFrame][2]);
            currentAnimationFrame++;
            if(currentAnimationFrame >= ANIMATION.length){
                currentAnimationFrame = 0;
                currentKeyFrame = 0;
            }
        }
        
        currentKeyFrame += 0.5;
        System.out.println(currentKeyFrame);
    }

    @Override
    public void update(){
        if(this.getTransform().YPos >= this.GROUND_HEIGHT){
            IS_DEAD = true;
        }

        // Player physics go here
        this.getTransform().YPos -=  (this.getTransform().YPos < this.GROUND_HEIGHT ? (vSpeed * FPS.getDeltaTime()) : 0);
        vSpeed += fallingSpeed * FPS.getDeltaTime();
        
        if(this.getTransform().XRot < 90){
            System.out.println(Math.abs((vSpeed * FPS.getDeltaTime()) * 10));
            this.getTransform().XRot -= Math.abs((vSpeed * FPS.getDeltaTime()) * 10);
        }
        
        animation();
    }
}
