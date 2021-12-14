package com.game.Managers;

import com.game.App;
import com.game.Entities.Player;

public class InputManager {
    public static void MousePressed(){
        SceneManager.getCurrentScene().mousePressed();
    }
}
