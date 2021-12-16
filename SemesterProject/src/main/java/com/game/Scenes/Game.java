/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.game.App;
import com.game.FPS;
import com.game.Audio.SFXEnum;
import com.game.Audio.SFXPlayer;
import com.game.Entities.Action;
import com.game.Entities.Button;
import com.game.Entities.Pipe;
import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Entities.SpriteData;
import com.game.Managers.RenderManager;
import com.game.Managers.SceneManager;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Game extends Scene {
    private boolean SAVED_SCORE = false;
    private boolean GAME_STARTED = false;
    private Player bird = null;
    private int timer = 0;
    private int score = 0;
    private Text scoreText = null;
    private ArrayList<Pipe[]> PIPE_PAIRS = new ArrayList<Pipe[]>();
    private ArrayList<Pipe[]> PIPES_TO_REMOVE = new ArrayList<Pipe[]>();
    private final int GENERATE_PIPE_TIME = 2000;
    private SceneType SCENE_TO_RET = SceneType.GAME;

    private ArrayList<Sprite> GAME_OVER_SPRITES = new ArrayList<Sprite>();
    private ArrayList<Sprite> GAME_INTRO_SCENE = new ArrayList<Sprite>();

    public Game(){	
        // Create Instance of bird
        bird = new Player(App.SPRITESHEET);
        this.SPRITES.add(bird);

        // Create Instance of text for score
        scoreText = new Text(String.valueOf(score));
        scoreText.setUserData(new SpriteData("score", 999));
        scoreText.setFont(App.FLAPPY_BIRD_FONT);
        scoreText.setFill(Color.WHITE);
        scoreText.setStroke(Color.BLACK);
        scoreText.setStrokeWidth(2);
        scoreText.setX((App.SCREEN_WIDTH - (scoreText.getBoundsInLocal().getWidth())) / 2);
        scoreText.setY(((App.SCREEN_HEIGHT / 2) - 200));
        scoreText.setTextAlignment(TextAlignment.CENTER);
        this.NODES.add(scoreText);
        

        // Generate Pipe Pairs
        PIPE_PAIRS.add(Pipe.GeneratePipePair());

        SetupGameIntroSprites();
        SetupGameOverSprites();
    }

    private void SetupGameOverSprites(){
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
        this.GAME_OVER_SPRITES.add(menuButton);

        Button retryButton = new Button(App.SPRITESHEET);
        retryButton.setXRect(354);
        retryButton.setYRect(152);
        
    	retryButton.setXSize(52);
    	retryButton.setYSize(29);

        retryButton.setWidth(retryButton.getXSize() * 2);
    	retryButton.setHeight(retryButton.getYSize() * 2);

        retryButton.getTransform().XPos = ((App.SCREEN_WIDTH - retryButton.getWidth()) / 2);
    	retryButton.getTransform().YPos = ((App.SCREEN_HEIGHT - retryButton.getHeight())) - RenderManager.GROUND_HEIGHT - 75;

        retryButton.setLayer(999);

        retryButton.setAction(new Action() {
            @Override
            public void onClick(){
                SceneManager.RestartScene();
            }
            
            @Override
            public void onHover() {
                // Do Nothing
            }
        });


        this.GAME_OVER_SPRITES.add(retryButton);

        Sprite gameOver = new Sprite(App.SPRITESHEET);
        gameOver.setXRect(395);
        gameOver.setYRect(59);

        gameOver.setXSize(96);
        gameOver.setYSize(21);
        gameOver.setLayer(999);

        gameOver.setWidth(gameOver.getXSize() * 2.5);
        gameOver.setHeight(gameOver.getYSize() * 2.5);
        gameOver.getTransform().XPos = (App.SCREEN_WIDTH - gameOver.getWidth()) / 2;
        gameOver.getTransform().YPos = App.SCREEN_HEIGHT / 4;

        this.GAME_OVER_SPRITES.add(gameOver);
    }
    
    private void SetupGameIntroSprites(){
        Player demoBird = new Player(App.SPRITESHEET);
        demoBird.setIsDemo(true);

        Sprite introImage = new Sprite(App.SPRITESHEET);
        introImage.setXRect(292);
        introImage.setYRect(91);

        introImage.setXSize(57);
        introImage.setYSize(49);
        introImage.setLayer(999);

        introImage.setWidth(introImage.getXSize() * 2.5);
        introImage.setHeight(introImage.getYSize() * 2.5);
        introImage.getTransform().XPos = (App.SCREEN_WIDTH - introImage.getWidth()) / 2;
        introImage.getTransform().YPos = App.SCREEN_HEIGHT / 2;


        Sprite getReady = new Sprite(App.SPRITESHEET);
        getReady.setXRect(295);
        getReady.setYRect(59);

        getReady.setXSize(92);
        getReady.setYSize(25);
        getReady.setLayer(999);

        getReady.setWidth(getReady.getXSize() * 2.5);
        getReady.setHeight(getReady.getYSize() * 2.5);
        getReady.getTransform().XPos = (App.SCREEN_WIDTH - getReady.getWidth()) / 2;
        getReady.getTransform().YPos = App.SCREEN_HEIGHT / 4;

        this.GAME_INTRO_SCENE.add(demoBird);
        this.GAME_INTRO_SCENE.add(introImage);
        this.GAME_INTRO_SCENE.add(getReady);
    }

    public void CheckCollision(){
        if(bird.getIsDead()){ return; }

        // Get All Children Nodes from the RENDER_PANE
        ObservableList<Node> nodes = RenderManager.RENDER_PANE.getChildren();
        
        // Find a Player object in the nodes
        Node playerNode = nodes.stream().filter(node -> ((SpriteData)(node.getUserData())).tag == Player.TAG).findFirst().orElse(null);
        if(playerNode == null){ return; }
        
        // Get all pipes in current scene
        List<Node> pipeNodes = nodes.stream().filter(node -> ((SpriteData)(node.getUserData())).tag == Pipe.TAG).collect(Collectors.toList());
        
        for(Node pipeNode : pipeNodes){
            // Thank you to invariant for the intersection solution!
            // https://stackoverflow.com/questions/20840587/how-to-use-intersect-method-of-node-class-in-javafx
            if(pipeNode.intersects(pipeNode.sceneToLocal(playerNode.localToScene(playerNode.getBoundsInLocal())))){
                SFXPlayer.PlaySFXEnum(SFXEnum.BIRD_HIT);
                bird.setIsDead(true);
            }
        }
        
    }
    
    @Override
    public SceneType Run(){
        if(!GAME_STARTED){
            this.GAME_INTRO_SCENE.forEach(sprite -> sprite.draw());
            this.GAME_INTRO_SCENE.forEach(sprite -> sprite.update());
            return SCENE_TO_RET;
        }

        if(bird.getIsDead()){ this.GAME_OVER_SPRITES.forEach(sprite -> sprite.draw()); }

        this.SPRITES.forEach(sprite -> sprite.draw());
        this.PIPE_PAIRS.forEach(pipes -> { 
            pipes[0].draw(); 
            pipes[1].draw();
        });
        this.NODES.forEach(node -> RenderManager.AddNode(node));

        this.SPRITES.forEach(sprite -> sprite.update());
        this.PIPE_PAIRS.forEach(pipes -> {
            // If the bird is not dead, then move the pipes
            if(!bird.getIsDead()){ pipes[0].update(); pipes[1].update(); }

            // Check if player in-between pipes - If so, increase score and set score to counted
            if((pipes[0].getTransform().XPos + (pipes[0].getWidth() / 2)) < (App.SCREEN_WIDTH / 2) - 50 && !pipes[0].isScoreCounted() && !pipes[1].isScoreCounted()){
                score++;
                scoreText.setText(String.valueOf(score));
                pipes[0].setScoreCounted(true);
                pipes[1].setScoreCounted(true);
                SFXPlayer.PlaySFXEnum(SFXEnum.PASS_PIPE);
            }
            
            // Add Pipes to be removed if pipes is off screen
            if(pipes[0].getTransform().XPos <= (pipes[0].getWidth() * -1)  || pipes[1].getTransform().XPos <= (pipes[1].getWidth() * -1)){ PIPES_TO_REMOVE.add(pipes); }
        });

        CheckCollision();

        if(PIPES_TO_REMOVE.size() > 0){
            System.out.println("Removing Pipes...");
            this.PIPE_PAIRS.removeAll(PIPES_TO_REMOVE);
            PIPES_TO_REMOVE.clear();
        }

        if(timer >= GENERATE_PIPE_TIME && !bird.getIsDead()){
            this.PIPE_PAIRS.add(Pipe.GeneratePipePair());
            timer = 0;
        }

        timer += (FPS.getDeltaTime() * 1000);
        return SCENE_TO_RET;
    }
    

    @Override
    public void mousePressed(MouseEvent event){
        if(!GAME_STARTED){ GAME_STARTED = true; return; }

        if(!bird.getIsDead()){
            this.bird.jump(event.isSecondaryButtonDown());
        }else if(!SAVED_SCORE){
            if(score > 0){ Leaderboard.AddScore(score); Leaderboard.SaveLeaderboardToFile(Leaderboard.LEADERBOARD_PATH); }
            SAVED_SCORE = true;
        }
    }
}
