package com.game.Spaces;

import com.game.Game;
import com.game.Player;
import com.game.Audio.MusicPlayer;
import com.game.Audio.SFXPlayer;
import com.game.Main;

public class StarSpace implements Space {
    private final String SPACE_TYPE = "Star";
    private int starPrice = 20;

    @Override
    public void Activate(Player player) {
        System.out.format("%s landed on a Star Space!\n", player.getName());

        if(player.getCoins() >= this.starPrice) {
            String userChoice;

            if(!player.getIsCPU())
                userChoice = Main.choice(String.format("Would you like to purchase a star for %s coins (Y|N)? ", this.starPrice), "(Y|N)", true);
            else
                userChoice = "Y";
            
            if(userChoice.equals("Y")){
                player.setCoins(player.getCoins() - this.starPrice);
                player.setStars(player.getStars() + 1);
                System.out.format("%s got a star!\n", player.getName());
                MusicPlayer.PauseMusic();
                SFXPlayer.PlayStarGet();
                Game.sleep(3200); // About the length of the Star SFX Clip
                MusicPlayer.ResumeMusic();
            }
        } else
            System.out.println("Not enough money for a star!");
    }

    @Override
    public String GetSpaceType() {
        return this.SPACE_TYPE;
    }
    
}
