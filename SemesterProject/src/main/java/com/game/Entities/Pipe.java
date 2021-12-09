package com.game.Entities;

import javafx.scene.image.Image;

public class Pipe extends Sprite{
    public Pipe(Image spritesheet){
        super(spritesheet);
        this.setLayer(0);
        this.setXRect(84);
        this.setYRect(323);
        this.setXSize(26);
        this.setYSize(160);
        this.setWidth(this.getXSize() * 2);
        this.setHeight(this.getYSize() * 2);
        this.setLayer(-1);
    }
}
