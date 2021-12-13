package com.game.Scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.game.App;
import com.game.Entities.Pipe;
import com.game.Entities.Player;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class MainMenu extends Scene {
    private Player bird = null;
    private ArrayList<Pipe[]> PIPE_PAIRS = new ArrayList<Pipe[]>();
    private ArrayList<Pipe[]> PIPES_TO_REMOVE = new ArrayList<Pipe[]>();
    private final int GENERATE_PIPE_TIME = 200;
    private int timer = 0;

    public MainMenu(){
        // Initialize sprite stuff
    	
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
    	
        bird = new Player(App.SPRITESHEET);
        bird.getTransform().XPos = ((App.SCREEN_WIDTH - bird.getWidth()) / 2);
        bird.getTransform().YPos = ((App.SCREEN_HEIGHT - bird.getHeight()) / 2);
        bird.getTransform().XRot = 0;

        PIPE_PAIRS.add(Pipe.GeneratePipePair());

        this.SPRITES.add(bird);
    }
    
    public void CheckCollision(){
        ObservableList<Node> nodes = RenderManager.RENDER_PANE.getChildren();
        Node playerNode = nodes.stream().filter(node -> node.getUserData() == Player.TAG).findFirst().orElse(null);
        if(playerNode == null){ return; }
        
        List<Node> pipeNodes = nodes.stream().filter(node -> node.getUserData() == Pipe.TAG).collect(Collectors.toList());
        
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
        this.PIPE_PAIRS.forEach(pipes -> { pipes[0].draw(); pipes[1].draw();});

        this.SPRITES.forEach(sprite -> sprite.update());
        this.PIPE_PAIRS.forEach(pipes -> { 
            if(!bird.getIsDead()){
                pipes[0].update(); pipes[1].update();
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

        timer++;
        return super.Run();
    }

    @Override
    public void mousePressed(){
        this.bird.jump();
    }
}
