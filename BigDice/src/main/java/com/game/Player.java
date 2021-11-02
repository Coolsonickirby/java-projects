/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;
import java.util.HashMap;

import com.game.Items.Item;

public class Player {
    private static final int ITEM_COUNT = 3;

    private String name;
    private int startingNumber;
    private int coins;
    private int stars;
    private Item[] items;
    private int currentPosition;
    private boolean isCPU;

    Player(){
        this.name = "";
        this.coins = 0;
        this.stars = 0;
        this.items = new Item[Player.ITEM_COUNT];
        this.startingNumber = 0;
        this.isCPU = true;
    }

    Player(String name, boolean isCPU) {
        this.name = name;
        this.coins = 0;
        this.stars = 0;
        this.items = new Item[Player.ITEM_COUNT];
        this.startingNumber = 0;
        this.isCPU = isCPU;
    }

    //#region Get Variables
    public String getName() { return this.name; }
    public int getStartingNumber() { return this.startingNumber; }
    public int getCoins() { return this.coins; }
    public int getStars() { return this.stars; }
    public Item[] getItems() { return this.items; }
    public Item getItem(int idx) { return this.items[idx]; }
    public boolean getIsCPU() { return this.isCPU; }
    public int getCurrentPosition() { return this.currentPosition; }
    //#endregion

    //#region Set Variables
    public void setName(String name) { this.name = name; }
    public void setCoins(int coins) { this.coins = coins < 0 ? 0 : coins; }
    public void setStars(int stars) { this.stars = stars < 0 ? 0 : stars; }
    public void setItem(int idx, Item item) { this.items[idx] = item; }
    public void setItems(Item[] items) { this.items = items; }
    public void setCurrentPosition(int currentPosition) { this.currentPosition = currentPosition; }
    public void setStartingNumber(int startingNumber) { this.startingNumber = startingNumber; }
    public void setIsCPU(boolean isCPU) { this.isCPU = isCPU; }
    //#endregion

    //#region Use Item Functions
    public void useItem(int idx) {
        this.items[idx].Activate(this);
        this.items[idx] = null;
    }

    public void useItem(int idx, HashMap<String, String> options) {
        this.items[idx].Activate(this, options);
        this.items[idx] = null;
    }

    public void useItem(int idx, Player targetPlayer) {
        this.items[idx].Activate(this, targetPlayer);
        this.items[idx] = null;
    }

    public void useItem(int idx, Player targetPlayer, HashMap<String, String> options) {
        this.items[idx].Activate(this, targetPlayer, options);
        this.items[idx] = null;
    }
    //#endregion
}
