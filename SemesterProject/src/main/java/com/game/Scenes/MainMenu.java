/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Scenes;

import com.game.App;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

public class MainMenu extends Scene {
    private SceneType SCENE_TO_RET = SceneType.MAIN_MENU;

    public MainMenu(){
        // Initialize sprite stuff    	
        // Game Name from spritesheet
    	Sprite title = new Sprite(App.SPRITESHEET);
    	title.setXRect(351);
    	title.setYRect(91);
    	
    	title.setXSize(89);
    	title.setYSize(24);
    	
    	title.setWidth(title.getXSize() * 3);
    	title.setHeight(title.getYSize() * 3);
    	title.getTransform().XPos = ((App.SCREEN_WIDTH - title.getWidth()) / 2);
    	title.getTransform().YPos = ((App.SCREEN_HEIGHT - title.getHeight()) / 2) - 200;
        
        this.SPRITES.add(title);

        // Play Button
    	Button playBtn = new Button(App.SPRITESHEET);
    	playBtn.setXRect(354);
    	playBtn.setYRect(118);
    	
    	playBtn.setXSize(52);
    	playBtn.setYSize(29);
    	
    	playBtn.setWidth(playBtn.getXSize() * 1.5);
    	playBtn.setHeight(playBtn.getYSize() * 1.5);
    	playBtn.getTransform().XPos = ((App.SCREEN_WIDTH - playBtn.getWidth()) / 2) - (playBtn.getWidth() + 20);
    	playBtn.getTransform().YPos = ((App.SCREEN_HEIGHT - playBtn.getHeight())) - RenderManager.GROUND_HEIGHT - 20;
    	
        playBtn.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.GAME;
            }
            
            @Override
            public void onHover() {
                System.out.println("Button Hover!");                
            }
        });

        this.SPRITES.add(playBtn);

        // Settings Button
    	Button settingsBtn = new Button(App.SPRITESHEET);
    	settingsBtn.setXRect(415);
    	settingsBtn.setYRect(152);
    	
    	settingsBtn.setXSize(52);
    	settingsBtn.setYSize(29);
    	
    	settingsBtn.setWidth(settingsBtn.getXSize() * 1.5);
    	settingsBtn.setHeight(settingsBtn.getYSize() * 1.5);
    	settingsBtn.getTransform().XPos = ((App.SCREEN_WIDTH - settingsBtn.getWidth()) / 2) + (settingsBtn.getWidth() + 20);
    	settingsBtn.getTransform().YPos = ((App.SCREEN_HEIGHT - settingsBtn.getHeight())) - RenderManager.GROUND_HEIGHT - 20;
    	
        settingsBtn.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.OPTIONS;
            }
            
            @Override
            public void onHover() {
                System.out.println("Button Hover!");                
            }
        });

        this.SPRITES.add(settingsBtn);

        // Settings Button
    	Button leaderBoardButton = new Button(App.SPRITESHEET);
    	leaderBoardButton.setXRect(414);
    	leaderBoardButton.setYRect(118);
    	
    	leaderBoardButton.setXSize(52);
    	leaderBoardButton.setYSize(29);
    	
    	leaderBoardButton.setWidth(leaderBoardButton.getXSize() * 1.5);
    	leaderBoardButton.setHeight(leaderBoardButton.getYSize() * 1.5);
    	leaderBoardButton.getTransform().XPos = ((App.SCREEN_WIDTH - leaderBoardButton.getWidth()) / 2);
    	leaderBoardButton.getTransform().YPos = ((App.SCREEN_HEIGHT - leaderBoardButton.getHeight())) - RenderManager.GROUND_HEIGHT - 20;
    	
        leaderBoardButton.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.LEADERBOARD;
            }
            
            @Override
            public void onHover() {
                System.out.println("Button Hover!");                
            }
        });

        this.SPRITES.add(leaderBoardButton);

        // Add Credits button
    	Button creditsButton = new Button(App.SPRITESHEET);
        creditsButton.setXRect(460);
        creditsButton.setYRect(101);

    	creditsButton.setXSize(40);
    	creditsButton.setYSize(14);
    	
    	creditsButton.setWidth(creditsButton.getXSize() * 1.5);
    	creditsButton.setHeight(creditsButton.getYSize() * 1.5);
    	creditsButton.getTransform().XPos = ((App.SCREEN_WIDTH - (creditsButton.getWidth()) - 20));
    	creditsButton.getTransform().YPos = 50;

        creditsButton.setAction(new Action() {
            @Override
            public void onClick(){
                SCENE_TO_RET = SceneType.CREDITS;
            }
            
            @Override
            public void onHover() {
                // Do Nothing
            }
        });
        
        creditsButton.setLayer(999);
    
        this.SPRITES.add(creditsButton);

        // Demo Birb
        Player demoBird = new Player(App.SPRITESHEET);
        demoBird.setIsDemo(true);
        this.SPRITES.add(demoBird);
    }

    @Override
    public SceneType Run(){
        SPRITES.forEach(sprite -> sprite.draw());
        SPRITES.forEach(sprite -> sprite.update());
        return SCENE_TO_RET;
    }
}
