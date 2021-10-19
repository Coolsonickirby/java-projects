package com.game.Items;

import java.util.HashMap;

import com.game.Main;
import com.game.Player;
import com.game.Audio.SFXPlayer;

public class BooBell implements Item {
    private final String ITEM_NAME = "Boo Bell";
    private final boolean HAS_TARGET = true;
    private final boolean REQUIRE_OPTIONS = true;

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

    }

    @Override
    public void Activate(Player srcPlayer, Player targetPlayer, HashMap<String, String> options) {
        System.out.format("%s used a %s!\n", srcPlayer.getName(), this.ITEM_NAME);
        if (options.get("steal").equals("star")) {
            if (srcPlayer.getCoins() >= 50 && targetPlayer.getStars() > 0) {
                srcPlayer.setCoins(srcPlayer.getCoins() - 50);

                targetPlayer.setStars(targetPlayer.getStars() - 1);
                srcPlayer.setStars(srcPlayer.getStars() + 1);
                SFXPlayer.PlayBooSteal();
                System.out.format("%s stole a star from %s!\n", srcPlayer.getName(), targetPlayer.getName());
            } else {
                if (srcPlayer.getCoins() < 50)
                    System.out.println("Not enough coins to steal a star!");
                else if (targetPlayer.getStars() <= 0)
                    System.out.format("%s doesn't have any stars to steal!\n", targetPlayer.getName());
            }
        } else if (options.get("steal").equals("coins")) {
            if (srcPlayer.getCoins() < 5) {
                System.out.println("Not enough coins to steal coins!");
            } else {
                int min = 0;
                int max = (targetPlayer.getCoins() / 2);
                int amountToSteal = Main.getRandomNumber(min, max);
                srcPlayer.setCoins(srcPlayer.getCoins() + amountToSteal);
                targetPlayer.setCoins(targetPlayer.getCoins() - amountToSteal);
                SFXPlayer.PlayBooSteal();
                System.out.format("%s successfully stole %s coin(s) from %s!\n", srcPlayer.getName(), amountToSteal,
                        targetPlayer.getName());
            }
        } else {
            System.out.println("Invalid Options Passed!");
        }
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
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("steal", Main.choice(
                "What do you want to steal?\n- Coins (Cost 5 coins)\n- Star (Cost 50 coins)\nYour Choice (coins|star): ",
                "(coins|star)", true));
        return options;
    }
}
