package com.game.Spaces;

import com.game.Player;

public interface Space {
    public void Activate(Player player);
    public String GetSpaceType();
}
