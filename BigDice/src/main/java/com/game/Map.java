/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;

import com.game.Spaces.*;

public class Map {
    private Space[] map;

    Map(int mapSize) {
        this.map = new Space[mapSize];
        for (int i = 0; i < mapSize; i++) {
            double d = Math.random();
            if (d < 0.5)
                this.map[i] = new BlueSpace(); // Blue Space
            else if (d < 0.7)
                this.map[i] = new RedSpace(); // Red Space
            else
                this.map[i] = new ItemSpace(); // Item Space
        }
        this.shuffleStarSpace();
    }

    Map(Space[] map) { this.map = map; }

    public void land(int position, Player player) {
        this.map[position].Activate(player);
        if (this.map[position] instanceof StarSpace) {
            this.map[position] = new BlueSpace();
            this.shuffleStarSpace();
        }
    }

    public void shuffleStarSpace() {
        this.map[this.getRandomSpot(BlueSpace.class)] = new StarSpace();
    }

    private int getRandomSpot(Class<?> typeOfSpace) {
        int randomSpot = Main.getRandomNumber(0, this.map.length);
        while (this.map[randomSpot].getClass() != typeOfSpace)
            randomSpot = Main.getRandomNumber(0, this.map.length);
        return randomSpot;
    }

    public int getMapSize() { return this.map.length; }
    public void setMap(Space[] map){ this.map = map; }
    public Space[] getMapArray(){ return this.map; }
}
