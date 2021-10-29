/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Items;

import java.util.HashMap;

import com.game.Player;
import com.game.Audio.SFXPlayer;

public class WrapBlock implements Item {
    private final String ITEM_NAME = "Wrap Block";
    private final boolean HAS_TARGET = true;
    private final boolean REQUIRE_OPTIONS = false;

    @Override
    public void Activate(Player player) {
        System.out.println("No target player!");
    }

    @Override
    public void Activate(Player player, HashMap<String, String> options) {
        Activate(player);
    }

    @Override
    public void Activate(Player srcPlayer, Player targetPlayer) {
        System.out.format("%s used a %s!\n", srcPlayer.getName(), this.ITEM_NAME);
        int srcPos = srcPlayer.getCurrentPosition();
        srcPlayer.setCurrentPosition(targetPlayer.getCurrentPosition());
        targetPlayer.setCurrentPosition(srcPos);
        SFXPlayer.PlayWrapBlock();
        System.out.format("Swapped %s\'s position with %s\'s!\n", srcPlayer.getName(), targetPlayer.getName());
    }

    @Override
    public void Activate(Player srcPlayer, Player targetPlayer, HashMap<String, String> options) {
        Activate(srcPlayer, targetPlayer);
    }

    @Override
    public String getItemName() {
        return this.ITEM_NAME;
    }

    @Override
    public boolean getHasTarget() {
        return this.HAS_TARGET;
    }

    @Override
    public boolean getRequireOptions() {
        return this.REQUIRE_OPTIONS;
    }

    @Override
    public HashMap<String, String> getOptions(Player player) {
        return null;
    }
}
