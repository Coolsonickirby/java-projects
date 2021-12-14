package com.game.Scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.game.App;
import com.game.FPS;
import com.game.Entities.Pipe;
import com.game.Entities.Player;
import com.game.Entities.SpriteData;
import com.game.Managers.RenderManager;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class Game extends Scene {
    private Player bird = null;
    private int timer = 0;
    private int score = 0;
    private Text scoreText = null;
    private ArrayList<Pipe[]> PIPE_PAIRS = new ArrayList<Pipe[]>();
    private ArrayList<Pipe[]> PIPES_TO_REMOVE = new ArrayList<Pipe[]>();
    private final int GENERATE_PIPE_TIME = 2000;
    private SceneType SCENE_TO_RET = SceneType.GAME;

    public Game(){	
        bird = new Player(App.SPRITESHEET);
        scoreText = new Text(String.valueOf(score));
        scoreText.setUserData(new SpriteData("score", 999));
        scoreText.setFont(App.FLAPPY_BIRD_FONT);
        // Set text color to white
        scoreText.setX(App.SCREEN_WIDTH / 2);
        scoreText.setY(((App.SCREEN_HEIGHT / 2) - 200));

        PIPE_PAIRS.add(Pipe.GeneratePipePair());

        this.SPRITES.add(bird);
        this.NODES.add(scoreText);
    }
    
    public void CheckCollision(){
        ObservableList<Node> nodes = RenderManager.RENDER_PANE.getChildren();
        Node playerNode = nodes.stream().filter(node -> ((SpriteData)(node.getUserData())).tag == Player.TAG).findFirst().orElse(null);
        if(playerNode == null){ return; }
        
        List<Node> pipeNodes = nodes.stream().filter(node -> ((SpriteData)(node.getUserData())).tag == Pipe.TAG).collect(Collectors.toList());
        
        for(Node pipeNode : pipeNodes){
            // Thank you to invariant for the intersection solution!
            // https://stackoverflow.com/questions/20840587/how-to-use-intersect-method-of-node-class-in-javafx
            if(pipeNode.intersects(pipeNode.sceneToLocal(playerNode.localToScene(playerNode.getBoundsInLocal())))){
                bird.setIsDead(true);
            }
        }
        
    }
    
    @Override
    public SceneType Run(){
        this.SPRITES.forEach(sprite -> sprite.draw());
        this.PIPE_PAIRS.forEach(pipes -> { 
            pipes[0].draw(); 
            pipes[1].draw();
        });
        this.NODES.forEach(node -> RenderManager.AddNode(node));

        this.SPRITES.forEach(sprite -> sprite.update());
        this.PIPE_PAIRS.forEach(pipes -> { 
            if(!bird.getIsDead()){
                pipes[0].update(); pipes[1].update();
            }

            // Check if player in-between pipes - If so, increase score and set score to counted
            if((pipes[0].getTransform().XPos + (pipes[0].getWidth() / 2)) < (App.SCREEN_WIDTH / 2) && !pipes[0].isScoreCounted() && !pipes[1].isScoreCounted()){
                score++;
                scoreText.setText(String.valueOf(score));
                pipes[0].setScoreCounted(true);
                pipes[1].setScoreCounted(true);
            }
            
            if(pipes[0].getTransform().XPos <= (pipes[0].getWidth() * -1)  || pipes[1].getTransform().XPos <= (pipes[1].getWidth() * -1)){
                PIPES_TO_REMOVE.add(pipes);
            }
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
    public void mousePressed(){
        if(bird.getIsDead()){
            SCENE_TO_RET = SceneType.MAIN_MENU;
        }

        this.bird.jump();
    }
}
