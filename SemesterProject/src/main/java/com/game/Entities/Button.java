package com.game.Entities;

import com.game.Audio.SFXEnum;
import com.game.Audio.SFXPlayer;

import javafx.scene.image.Image;

public class Button extends Sprite {
    private Action actions;

    public Button(Image spritesheet) {
        super(spritesheet);
        this.actions = new Action() {
            @Override
            public void onClick(){
                System.out.println("Button clicked!");
            }
            
            @Override
            public void onHover() {
                System.out.println("Button Hover!");                
            }
        };
    }

    public Action getAction(){
        return this.actions;
    }

    public void setAction(Action action){
        this.actions = action;
    }

    @Override
    public void onClick(){
        SFXPlayer.PlaySFXEnum(SFXEnum.BUTTON_HIT);
        this.actions.onClick();
    }

    @Override
    public void onHover(){
        this.actions.onHover();
    }
    
}
