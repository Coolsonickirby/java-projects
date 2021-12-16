package com.game.Scenes;

import java.util.Arrays;

import com.game.App;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.SpriteData;
import com.game.Managers.RenderManager;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Credits extends Scene {
    private SceneType SCENE_TO_RET = SceneType.CREDITS;
    private static String[] CREDITS_ARRAY = new String[]{
        "Ali Hussain - Programmer",
        "Superjustinbros - Spritesheet",
        "StackOverFlow - Answering Programming Questions",
        "Majora & Catalyst - Beta Testing",
        "Kimberly Moscardelli - For letting me work on these fun projects \nand for being a cool professor!",
        "and YOU for playing my game!"
    };
    private static Text CREDITS = new Text(String.join("\n", Arrays.asList(CREDITS_ARRAY)));

    public static void StaticSetup(){
        CREDITS.setUserData(new SpriteData("score", 999));
        CREDITS.setFont(App.FLAPPY_BIRD_FONT_SMALL);
        CREDITS.setFill(Color.WHITE);
        CREDITS.setStroke(Color.BLACK);
        CREDITS.setStrokeWidth(2);
        CREDITS.setX((App.SCREEN_WIDTH - (CREDITS.getBoundsInLocal().getWidth())) / 2);
        CREDITS.setY(((App.SCREEN_HEIGHT / 2) - 200));
        CREDITS.setTextAlignment(TextAlignment.CENTER);
    }

    public Credits(){
        // Add back button
    	Button menuButton = new Button(App.SPRITESHEET);
        menuButton.setXRect(462);
        menuButton.setYRect(26);

    	menuButton.setXSize(40);
    	menuButton.setYSize(14);
    	
    	menuButton.setWidth(menuButton.getXSize() * 2);
    	menuButton.setHeight(menuButton.getYSize() * 2);
    	menuButton.getTransform().XPos = ((App.SCREEN_WIDTH - menuButton.getWidth()) / 2);
    	menuButton.getTransform().YPos = ((App.SCREEN_HEIGHT - menuButton.getHeight())) - RenderManager.GROUND_HEIGHT - 20;

        menuButton.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.MAIN_MENU;
            }
            
            @Override
            public void onHover() {
                // Do Nothing
            }
        });
        
        menuButton.setLayer(999);
    
        this.SPRITES.add(menuButton);
        this.NODES.add(CREDITS);
    }

    @Override
    public SceneType Run(){
        this.SPRITES.forEach(sprite -> sprite.draw());
        this.NODES.forEach(node -> RenderManager.AddNode(node));
        return SCENE_TO_RET;
    }
}
