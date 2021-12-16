/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Entities;

import com.game.App;
import com.game.FPS;

import javafx.scene.image.Image;

public class Pipe extends Sprite{
    public static final String TAG = "pipe";
    private int currentPipeColor = 0;
    private int[][] PIPE_COLORS = new int[][]{
        new int[]{84, 323},
        new int[]{0, 323}
    };
    private boolean scoreCounted = false;
    public Pipe(Image spritesheet){
        super(spritesheet);
        this.currentPipeColor = App.getRandomNumber(0, PIPE_COLORS.length);
        this.setLayer(0);
        this.setXRect(PIPE_COLORS[currentPipeColor][0]);
        this.setYRect(PIPE_COLORS[currentPipeColor][1]);
        this.setXSize(26);
        this.setYSize(160);
        this.setWidth(this.getXSize() * 2);
        this.setHeight(this.getYSize() * 2);
        this.setTag(Pipe.TAG);
        this.setLayer(-1);
    }
    

    public boolean isScoreCounted() {
        return scoreCounted;
    }


    public void setScoreCounted(boolean scoreCounted) {
        this.scoreCounted = scoreCounted;
    }


    public static Pipe[] GeneratePipePair(){
        // Set X and Y Pos
        
        int space = App.getRandomNumber(0, 200);

        Pipe topPipe = new Pipe(App.SPRITESHEET);
        topPipe.getTransform().XPos = App.SCREEN_WIDTH + 25;
        topPipe.getTransform().YPos = space * -1;
        topPipe.getTransform().YScale = -1;
        
        // Set X and Y Pos
        Pipe bottomPipe = new Pipe(App.SPRITESHEET);
        bottomPipe.getTransform().XPos = App.SCREEN_WIDTH + 25;
        bottomPipe.getTransform().YPos = App.SCREEN_HEIGHT - (space + 90);

        return new Pipe[]{topPipe, bottomPipe};
    }

    @Override
    public void update(){
        this.getTransform().XPos -= (FPS.getDeltaTime() * 100);
    }
}
