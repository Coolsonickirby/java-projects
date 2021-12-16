/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Scenes;
import com.game.Entities.Sprite;
import com.game.Managers.RenderManager;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Scene {
    public ArrayList<Sprite> SPRITES = new ArrayList<Sprite>();
    public ArrayList<Node> NODES = new ArrayList<Node>();

    public SceneType Run(){
        SPRITES.forEach(sprite -> sprite.draw());
        NODES.forEach(node -> RenderManager.AddNode(node));
        SPRITES.forEach(sprite -> sprite.update());
        return SceneType.MAIN_MENU;
    }

    public void mousePressed(MouseEvent event){
        // Do nothing
    }
}
