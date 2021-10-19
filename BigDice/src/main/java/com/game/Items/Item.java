package com.game.Items;

import java.util.HashMap;

import com.game.Player;

public interface Item {
    public void Activate(Player player);

    public void Activate(Player player, HashMap<String, String> options);

    public void Activate(Player srcPlayer, Player targetPlayer);

    public void Activate(Player srcPlayer, Player targetPlayer, HashMap<String, String> options);

    public String getItemName();

    public boolean getHasTarget();

    public boolean getRequireOptions();

    public HashMap<String, String> getOptions(Player player);
}
