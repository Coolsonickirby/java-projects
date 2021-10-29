/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Spaces;

import com.game.Player;
import com.game.Items.*;

public class ItemSpace implements Space {
    private final String SPACE_TYPE = "Item";

    @Override
    public void Activate(Player player) {
        System.out.format("%s landed on a Item Space!\n", player.getName());
        boolean gotItem = false;
        for (int i = 0; i < player.getItems().length; i++) {
            if (player.getItems()[i] == null) {
                Item res;
                double d = Math.random();
                if (d < 0.3)
                    res = new Mushroom();
                else if (d < 0.6)
                    res = new WrapBlock();
                else
                    res = new BooBell();
                player.setItem(i, res);
                gotItem = true;
                break;
            }
        }
        if (gotItem)
            System.out.println("Got a item!");
        else
            System.out.println("All item slots already full!");
    }
    
    @Override
    public String GetSpaceType() {
        return this.SPACE_TYPE;
    }
}
