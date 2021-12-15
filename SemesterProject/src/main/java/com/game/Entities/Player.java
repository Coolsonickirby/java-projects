// Physics copied from here --- https://gamedev.stackexchange.com/questions/70268/can-someone-explain-flappy-birds-physics-to-me
package com.game.Entities;
import com.game.App;
import com.game.FPS;
import com.game.Audio.SFXEnum;
import com.game.Audio.SFXPlayer;
import com.game.Managers.RenderManager;

import javafx.scene.image.Image;

public class Player extends Sprite {
    public static final String TAG = "player";
    private double vSpeed = 0.0;
    private double jSpeed = 130;
    private double fallingSpeed = -200;
    private boolean IS_DEAD = false;
    private boolean IS_DEMO = false;
    private double currentKeyFrame = 0;
    private int currentAnimationFrame = 0;
    private double GROUND_HEIGHT = 0;
    private double INITAL_Y_POS = 0;
    private double DEMO_BOUNCE = 2;
    private boolean IGNORE = false;
    private int[][] ANIMATION = new int[][] {
        new int[] { 0, 3, 491 },
        new int[] { 20, 31, 491 },
        new int[] { 35, 59, 491 },
        new int[] { 50, 31, 491 },
        new int[] { 65, 3, 491 },
    };

    public Player(Image spritesheet){
        super(spritesheet);
        this.setLayer(1);
        this.setXRect(3);
        this.setYRect(491);
        this.setXSize(17);
        this.setYSize(12);
        this.setWidth(this.getXSize() * 2);
        this.setHeight(this.getYSize() * 2);
        this.getTransform().XPos = ((App.SCREEN_WIDTH - this.getWidth()) / 2);
        INITAL_Y_POS = (App.SCREEN_HEIGHT - this.getHeight()) / 2;
        this.getTransform().YPos = INITAL_Y_POS;
        this.setTag(Player.TAG);
        this.GROUND_HEIGHT = ((App.SCREEN_HEIGHT - RenderManager.GROUND_HEIGHT) - this.getHeight());
    }
    
    public boolean getIsDead() { return IS_DEAD; }
    public void setIsDead(boolean IS_DEAD) { this.IS_DEAD = IS_DEAD; }

    public boolean getIsDemo() { return IS_DEMO; }
    public void setIsDemo(boolean IS_DEMO) { this.IS_DEMO = IS_DEMO; }

    public void jump(boolean invertJump){
        if(!IS_DEAD) {
            SFXPlayer.PlaySFXEnum(SFXEnum.BIRD_FLAP);
        	vSpeed = invertJump ? (jSpeed * -1) : jSpeed;
            this.getTransform().XRot = -50;
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
    }

    @Override
    public void update(){
        if(IS_DEMO){
            if(this.getTransform().YPos < (INITAL_Y_POS + DEMO_BOUNCE) && !IGNORE){
                this.getTransform().YPos += (FPS.getDeltaTime() * 10);
                // this.getTransform().YPos += 1;
            }else if(this.getTransform().YPos > (INITAL_Y_POS - DEMO_BOUNCE)){
                this.getTransform().YPos -= (FPS.getDeltaTime() * 10);
                // this.getTransform().YPos -= 1;
                IGNORE = true;
            }else {
                IGNORE = false;
            }

            animation();
            return;
        }

        if((this.getTransform().YPos >= this.GROUND_HEIGHT || this.getTransform().YPos <= 0) && !IS_DEAD){
            SFXPlayer.PlaySFXEnum(SFXEnum.BIRD_HIT);
            IS_DEAD = true;
        }

        // Player physics go here
        this.getTransform().YPos -=  (this.getTransform().YPos < this.GROUND_HEIGHT ? (vSpeed * FPS.getDeltaTime()) : 0);
        vSpeed += fallingSpeed * FPS.getDeltaTime();
        
        if(this.getTransform().XRot < 90){
            this.getTransform().XRot += 0.5;
        }
        
        animation();
    }
}
