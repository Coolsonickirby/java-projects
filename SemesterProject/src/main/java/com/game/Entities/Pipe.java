package com.game.Entities;

import com.game.App;
import com.game.FPS;

import javafx.scene.image.Image;

public class Pipe extends Sprite{
    public static final String TAG = "pipe";
    private boolean scoreCounted = false;
    public Pipe(Image spritesheet){
        super(spritesheet);
        this.setLayer(0);
        this.setXRect(84);
        this.setYRect(323);
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
