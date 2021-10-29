/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Spaces;
import com.game.Game;
import com.game.Player;

public class BlueSpace implements Space {
    private final String SPACE_TYPE = "Blue";

    @Override
    public void Activate(Player player) {
        int amountOfCoins = Game.getTurns() <= 5 ? 6 : 3;
        player.setCoins(player.getCoins() + amountOfCoins);
        System.out.format("%s landed on a Blue Space! Got %s coins!\n", player.getName(), amountOfCoins);
    }
    
    @Override
    public String GetSpaceType() {
        return this.SPACE_TYPE;
    }
}
