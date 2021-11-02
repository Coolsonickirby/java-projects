/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Spaces;

import com.game.Player;

public interface Space {
    public void Activate(Player player);
    public String GetSpaceType();
}
