package com.game.Items;

import java.util.HashMap;

import com.game.Dice;
import com.game.Player;
import com.game.Audio.SFXPlayer;
import com.game.Main;

public class Mushroom implements Item {
    private final String ITEM_NAME = "Mushroom";
    private final boolean HAS_TARGET = false;
    private final boolean REQUIRE_OPTIONS = false;

    @Override
    public void Activate(Player player) {
        if (!player.getIsCPU()){
            SFXPlayer.PlayDiceRoll();
            Main.prompt("Press enter to throw die...", false);
        }
        Dice.roll();
        SFXPlayer.PlayDiceLand();
        player.setCurrentPosition(player.getCurrentPosition() + Dice.getNum());
        System.out.format("%s used a mushroom and rolled a %s!\n", player.getName(), Dice.getNum());
    }

    @Override
    public void Activate(Player player, HashMap<String, String> options) {
        Activate(player);
    }

    @Override
    public void Activate(Player srcPlayer, Player targetPlayer) {
        Activate(srcPlayer);
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
