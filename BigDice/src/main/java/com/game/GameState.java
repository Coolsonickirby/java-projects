/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;

// private String name;
// private int startingNumber;
// private int coins;
// private int stars;
// private Item[] items;
// private int currentPosition;
// private boolean isCPU;

/*
 *
 * Player Struct {
 *      uint8 startingNumber
 *      uint32 nameLength
 *      char[nameLength] playerName
 *      uint32 coins
 *      uint32 stars
 *      uint8 amountOfItems
 *      uint8[amountOfItems] items
 *      ------ Type of Items
 *      --------- 0 | Mushroom
 *      --------- 1 | Wrap Block
 *      --------- 2 | Boo Bell
 *      --------- -1 (255 unsigned) | None
 *      uint32 currentPosition // Cannot be greater than mapSize
 *      uint8 isCPU
 * }
 * 
 * 
 * GameState File Structure
 * char[4] magic | MPGS
 * uint8 gameStarted
 * uint32 turns
 * uint32 mapSize
 * uint8[mapSize] spots
 * ------ Type of Spots
 * --------- 0 | Blue
 * --------- 1 | Red
 * --------- 2 | Item
 * --------- 3 | Star
 * --------- -1 (255 unsigned) | Invalid Space (Default to Blue)
 * 
 * uint32 playersCount
 * Player[playersCount] players
 * uint32 current_player
 */

// private Map map;
// private static int turns;
// private Player[] players;
// private int current_player;

import java.io.*;
import java.nio.ByteOrder;
import java.util.*;

import com.game.Spaces.*;
import com.game.Audio.SFXPlayer;
import com.game.Items.*;

public class GameState {

    public static final String MAGIC = "MPGS";
    public static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    public static final List<Class<?>> SPACE_TYPE = Arrays.asList(new Class<?>[] { 
        BlueSpace.class,
        RedSpace.class,
        ItemSpace.class,
        StarSpace.class 
    });

    public static final List<Class<?>> ITEM_TYPE = Arrays.asList(new Class<?>[] { 
        Mushroom.class,
        WrapBlock.class,
        BooBell.class 
    });

    public static boolean saveGameState(Game currentGameState, String output) {
        try {
            SFXPlayer.PlaySaveProgress();
            OutputStream outputStream = new FileOutputStream(output);

            // Write Magic
            Utils.WriteString(outputStream, GameState.MAGIC);

            // Write whether game started or not
            Utils.WriteUInt8(outputStream, currentGameState.getGameStarted() ? 1 : 0);

            // Write Game Turns
            Utils.WriteUInt32(outputStream, Game.getTurns(), BYTE_ORDER);

            // #region Write Map Information
            Space[] gameMap = currentGameState.getMap().getMapArray();
            Utils.WriteUInt32(outputStream, gameMap.length, BYTE_ORDER);

            int spaceType = 255;
            for (int i = 0; i < gameMap.length; i++) {
                spaceType = gameMap[i] == null ? -1 : SPACE_TYPE.indexOf(gameMap[i].getClass()); // -1 (255 unsigned) if it can't find a spot
                Utils.WriteUInt8(outputStream, spaceType);
            }
            // #endregion

            // #region Write Players Information
            Player[] gamePlayers = currentGameState.getPlayers();
            Utils.WriteUInt32(outputStream, gamePlayers.length, BYTE_ORDER);

            for (int i = 0; i < gamePlayers.length; i++) {
                // Write Player Starting Number
                Utils.WriteUInt8(outputStream, gamePlayers[i].getStartingNumber());
                
                // Write Player Name Length
                Utils.WriteUInt32(outputStream, gamePlayers[i].getName().length(), BYTE_ORDER);

                // Write Player Name
                Utils.WriteString(outputStream, gamePlayers[i].getName());

                Utils.WriteUInt32(outputStream, gamePlayers[i].getCoins(), BYTE_ORDER);
                Utils.WriteUInt32(outputStream, gamePlayers[i].getStars(), BYTE_ORDER);

                // #region Write Items
                Item[] items = gamePlayers[i].getItems();
                Utils.WriteUInt8(outputStream, items.length);

                int itemType = 0;
                for (int y = 0; y < items.length; y++) {
                    itemType = items[y] == null ? -1 : ITEM_TYPE.indexOf(items[y].getClass()); // -1 (255 unsigned) if
                                                                                               // it can't find a item
                    Utils.WriteUInt8(outputStream, itemType);
                }
                // #endregion

                Utils.WriteUInt32(outputStream, gamePlayers[i].getCurrentPosition(), BYTE_ORDER);
                Utils.WriteUInt8(outputStream, gamePlayers[i].getIsCPU() ? 1 : 0);
            }

            Utils.WriteUInt32(outputStream, currentGameState.getCurrentPlayer(), BYTE_ORDER);
            // #endregion

            // Close output stream
            outputStream.close();

            Game.sleep(Main.getRandomNumber(1500, 3000)); // Add time stop so Save Progress sfx actually plays for a bit longer

            return true;
        } catch (Exception e) {
            if(Main.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("[GameState::saveGameState] Error occured with the outputStream!");
        }
        return false;
    };

    public static Game loadGameState(String input) {
        try {
            InputStream inputStream = new FileInputStream(input);
            
            // Get and check magic
            String magic = Utils.ReadString(inputStream, GameState.MAGIC.length());
            if(!magic.equals(GameState.MAGIC)){
                System.out.println("[GameState::loadGameState] Invalid Magic!");
                if(Main.choice("Would you still like to try and load the save file? (Will most likely not work.) (Y|N) ", "(Y|N)", true) == "N")
                    return null;
            }

            boolean gameStarted = Utils.ReadUInt8(inputStream) != 0;
            int turns = Utils.ReadUInt32(inputStream, BYTE_ORDER);
            int mapSize = Utils.ReadUInt32(inputStream, BYTE_ORDER);

            //#region Setup Map
            int type = 255;
            Space[] mapSpots = new Space[mapSize];
            for(int i = 0; i < mapSpots.length; i++){
                type = Utils.ReadUInt8(inputStream);
                try {
                    mapSpots[i] = (Space)(SPACE_TYPE.get(type)).getConstructor().newInstance();
                } catch (IndexOutOfBoundsException e) {
                    mapSpots[i] = new BlueSpace();
                }
            }
            //#endregion

            //#region Setup Players
            int playerCount = Utils.ReadUInt32(inputStream, BYTE_ORDER);
            Player[] players = new Player[playerCount];
            for(int i = 0; i < players.length; i++){
                
                players[i] = new Player();
                players[i].setStartingNumber(Utils.ReadUInt8(inputStream));
                players[i].setName(Utils.ReadString(inputStream, Utils.ReadUInt32(inputStream, BYTE_ORDER)));
                players[i].setCoins(Utils.ReadUInt32(inputStream, BYTE_ORDER));
                players[i].setStars(Utils.ReadUInt32(inputStream, BYTE_ORDER));
                
                Item[] items = new Item[Utils.ReadUInt8(inputStream)];
                
                int itemType = 255;
                for(int y = 0; y < items.length; y++){
                    itemType = Utils.ReadUInt8(inputStream);
                    try {
                        items[y] = (Item)(ITEM_TYPE.get(itemType)).getConstructor().newInstance();
                    } catch (IndexOutOfBoundsException e) {
                        items[y] = null;
                    }
                }

                players[i].setItems(items);
                players[i].setCurrentPosition(Utils.ReadUInt32(inputStream, BYTE_ORDER));
                players[i].setIsCPU(Utils.ReadUInt8(inputStream) != 0); // If it's not 0, then it's a computer. Else, it's a human
            }


            // Get Current Player
            int currentPlayer = Utils.ReadUInt32(inputStream, BYTE_ORDER);

            //#endregion
            
            inputStream.close();
            
            return new Game(players, new Map(mapSpots), turns, currentPlayer, gameStarted);
        } catch (Exception e) {
            if(Main.IS_DEBUG){ e.printStackTrace(); }
            System.out.println("[GameState::loadGameState] Error occured with the inputStream!");
        }

        return null;
    }
}
