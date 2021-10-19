package com.game.Spaces;
import com.game.Game;
import com.game.Player;

public class RedSpace implements Space {
    private final String SPACE_TYPE = "Red";

    @Override
    public void Activate(Player player) {
        int amountOfCoins = Game.getTurns() <= 5 ? 6 : 3;
        player.setCoins(player.getCoins() - amountOfCoins);
        System.out.format("%s landed on a Red Space! Lost %s coins!\n", player.getName(), amountOfCoins);
    }
    
    @Override
    public String GetSpaceType() {
        return this.SPACE_TYPE;
    }
}
