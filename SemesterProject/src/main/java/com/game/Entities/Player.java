package com.game.Entities;

import com.game.App;
import com.game.FPS;

import javafx.scene.image.Image;

public class Player extends Sprite {
    private double vSpeed = 0.0;
    private double jSpeed = 300;
    private double fallingSpeed = 50;

    public Player(Image spritesheet){
        super(spritesheet);
    }

    @Override
    public void onClick(){
        System.out.println("Player clicked!");
        vSpeed = jSpeed;
    }

    @Override
    public void update(){
        // Player physics go here
        // System.out.println(FPS.getDeltaTime());
        // this.getTransform().YPos += vSpeed * FPS.getDeltaTime();
        // vSpeed += fallingSpeed * FPS.getDeltaTime();
    }
}
