package com.game.Managers;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.App;
import com.game.Scenes.Game;
import com.game.Scenes.MainMenu;
import com.game.Scenes.Scene;
import com.game.Scenes.SceneType;

public class SceneManager {
    public static boolean IS_READY = false;
    private static HashMap<SceneType, Class<?>> SCENE_MAP = new HashMap<SceneType, Class<?>>();
    private static ArrayList<SceneType> SCENE_HISTORY = new ArrayList<SceneType>();
    private static SceneType LAST_SCENE = null;
    private static SceneType CURRENT_SCENE = SceneType.MAIN_MENU;
    private static Scene ACTIVE_SCENE = null;

    public static void Setup(){
        SCENE_MAP.put(SceneType.MAIN_MENU, MainMenu.class);
        SCENE_MAP.put(SceneType.GAME, Game.class);
        SceneManager.IS_READY = true;
    }

    public static void Run(){
        if(CURRENT_SCENE != LAST_SCENE){
            LAST_SCENE = CURRENT_SCENE;
            SCENE_HISTORY.add(LAST_SCENE);
            ACTIVE_SCENE = null;
        }

        if(ACTIVE_SCENE == null){
            try {
                ACTIVE_SCENE = (Scene)(SCENE_MAP.get(CURRENT_SCENE).getConstructor().newInstance());
            } catch (Exception e) {
                if(App.IS_DEBUG){ e.printStackTrace(); }
                System.out.println("Failed loading scene " + CURRENT_SCENE.getClass().getName() + " ! Will default to MAIN_MENU !" );
                ACTIVE_SCENE = new MainMenu();
            }
        }
        
        CURRENT_SCENE = ACTIVE_SCENE.Run();
    }

    public static Scene getCurrentScene() {
        return ACTIVE_SCENE;
    }
}
