/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;

import java.util.*;

import com.game.Audio.*;
import com.game.Items.*;

public class Game {
    private Map map;
    private static int turns;
    private Player[] players;
    private int current_player;
    private boolean gameStarted;

    Game(Player[] players, int mapSize, int turns) {
        this.players = players;
        this.map = new Map(mapSize);
        Game.turns = turns;
        this.current_player = 0;
        this.gameStarted = false;
    }
    
    Game(Player[] players, Map gameMap, int turns, int current_player, boolean gameStarted) {
        this.players = players;
        this.map = gameMap;
        Game.turns = turns;
        this.current_player = current_player;
        this.gameStarted = gameStarted;
    }

    public static void sleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            if(Main.IS_DEBUG){ e.printStackTrace(); }
        }
    }
    
    public static void sleep(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            if(Main.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    private void UserMenu() {
        boolean usedItem = false;
        menuLoop: while (true) {
            String choice = Main.choice(
                    "Select an option:\n- Roll Die (R)\n- Items (I)\n- View Rankings (P)\n- View Positions On Map (M)\n- Save Game State (S)\n- Quit Game (Q)\nYour Choice: ", "(R|I|P|M|S|Q)", false);
            switch (choice) {
                case "R":
                    SFXPlayer.PlayDiceRoll();
                    Main.prompt("Press enter to throw the die...", false);
                    Dice.roll();
                    SFXPlayer.PlayDiceLand();
                    System.out.format("Rolled a %s!\n", Dice.getNum());
                    break menuLoop;
                case "I":
                    if (usedItem)
                        System.out.println("Already used an item!");
                    else
                        usedItem = this.ShowItemMenu();
                    break;
                case "P":
                    this.PrintRankings(false);
                    break;
                case "M":
                    this.PrintPositions();
                    break;
                case "S":
                    this.SaveMenu();
                    break;
                case "Q":
                    String quitChoice = Main.choice("Are you sure you want to close the game? (Y|N) ", "(Y|N)", true);
                    if(quitChoice.equals("Y")){
                        String saveChoice = Main.choice("Would you like to save this game state? (Y|N) ", "(Y|N)", true);
                        if(saveChoice.equals("Y")){ this.SaveMenu(); Game.sleep(2000); }
                        System.exit(0);
                    }
                    break;
            }
        }
    }

    private void PrintPositions(){
        Main.PrintSeperator();
        System.out.println("Players Positions:");
        System.out.format("%-20s - %-20s - %-20s\n", "Player Name", "Space Type", "Position");
        Main.PrintSeperator();
        for(int i = 0; i < this.players.length; i++){
            System.out.format("%-20s - %-20s - %-20s\n", this.players[i].getName(), this.map.getMapArray()[this.players[i].getCurrentPosition()].GetSpaceType(), this.players[i].getCurrentPosition() > 0 ? this.players[i].getCurrentPosition() : "Start");
        }
        Main.PrintSeperator();
    }

    private void SaveMenu(){
        SFXPlayer.PlaySavePrompt();
        String fileLocation = Main.choice("Enter game state name (Enter 'Back' to go back): ", "^[\\w\\-. ]+$", false);
        
        if(fileLocation.equals("Back")){ return; }

        if(!fileLocation.endsWith(".mpcs")) { fileLocation += ".mpcs"; }

        System.out.println("Saving game state to file.....");
        if(GameState.saveGameState(this, fileLocation)){
            SFXPlayer.PlaySaveComplete();
            System.out.println("Saved game state successfully!");
        }
        else{
            System.out.println("Failed saving game state!");
        }
        
    }

    private void PrintRankings(boolean printWinner) {
        Player[] rankings = this.players.clone();

        Arrays.sort(rankings, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                int starComp = Integer.valueOf(p2.getStars()).compareTo(Integer.valueOf(p1.getStars()));
                if (starComp != 0)
                    return starComp;

                int coinComp = Integer.valueOf(p2.getCoins()).compareTo(Integer.valueOf(p1.getCoins()));
                return coinComp;
            }
        });


        Main.PrintSeperator();
        System.out.println("Players Rankings:");
        for (int i = 0; i < rankings.length; i++)
            System.out.format("%s. %-20s | Stars - %s | Coins - %s\n", i + 1, rankings[i].getName(), rankings[i].getStars(), rankings[i].getCoins());
        Main.PrintSeperator();
        
        if (printWinner){ System.out.format("The winner is %s!\n", rankings[0].getName()); }
    }

    private boolean ShowItemMenu() {
        itemLoop: while (true) {
            Player currentPlayer = players[this.current_player];
            Item[] items = currentPlayer.getItems();
            String itemsOutput = "";
            boolean itemExists = false;

            List<String> options = new ArrayList<String>();
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    itemsOutput += String.format("%s - %s\n", i, items[i].getItemName());
                    options.add(Integer.toString(i));
                    itemExists = true;
                }
            }

            if (!itemExists) {
                System.out.println("No Items Found!");
                return false;
            }

            System.out.println("Items: ");
            System.out.print(itemsOutput);

            options.add("N");
            String filter = String.format("(%s)", String.join("|", options));
            String choice = Main.choice(String.format("Please select a item %s: ", filter), filter, true);

            if (choice.equals("N"))
                return false;

            int itemIdx = Integer.parseInt(choice);

            if (currentPlayer.getItem(itemIdx).getHasTarget()) {
                String target = getTarget();
                if (target.equals("N"))
                    continue itemLoop;

                int targetIdx = Integer.parseInt(target);

                if (currentPlayer.getItem(itemIdx).getRequireOptions()) {
                    currentPlayer.useItem(itemIdx, this.players[targetIdx],
                            currentPlayer.getItem(itemIdx).getOptions(currentPlayer));
                } else {
                    currentPlayer.useItem(itemIdx, this.players[targetIdx]);
                }

                return true;
            } else {
                currentPlayer.useItem(itemIdx);
                return true;
            }
        }
    }

    private String getTarget() {
        String targets = "";
        List<String> options = new ArrayList<String>();
        for (int i = 0; i < this.players.length; i++) {
            if (i != this.current_player) {
                targets += String.format("%s - %s\n", i, this.players[i].getName());
                options.add(Integer.toString(i));
            }
        }
        options.add("N");
        System.out.println("Targets: ");
        System.out.print(targets);
        String filter = String.format("(%s)", String.join("|", options));
        return Main.choice(String.format("Select your target %s: ", filter), filter, true);
    }

    public void Start() {
        // Introduce all players
        Main.PrintSeperator(); // --------------------------------------

        System.out.println("All Players: ");
        for (Player player : players)
            System.out.format("%s - %s\n", player.getName(), player.getIsCPU() ? "CPU" : "Human");

        Main.PrintSeperator(); // --------------------------------------

        // Set player turn order logic
        System.out.println("Rolling for Player Turn Order!");
        List<Integer> orderNumbers = new ArrayList<Integer>();
        for (Player player : this.players) {
            System.out.format("%s\'s turn to roll!\n", player.getName());
            if (!player.getIsCPU()) {
                SFXPlayer.PlayDiceRoll();
                Main.prompt("Press enter to throw the die...", false);
            }
            
            Dice.roll();
            
            while (orderNumbers.contains(Dice.getNum()))
                Dice.roll();
            
            SFXPlayer.PlayDiceLand();
            player.setStartingNumber(Dice.getNum());
            orderNumbers.add(Dice.getNum());
            System.out.format("%s rolled a %s!\n", player.getName(), Dice.getNum());
            Game.sleep();
        }

        Arrays.sort(this.players, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return Integer.valueOf(p2.getStartingNumber()).compareTo(Integer.valueOf(p1.getStartingNumber()));
            }
        });

        Main.PrintSeperator(); // --------------------------------------

        System.out.println("Player Orders: ");
        for (int i = 0; i < this.players.length; i++)
            System.out.format("%s - %s\n", i + 1, this.players[i].getName());
        Game.sleep();

        Main.PrintSeperator(); // --------------------------------------

        // Everyone starts with 10 coins
        System.out.println("Giving 10 coins to everyone...");

        for (Player player : this.players)
            player.setCoins(player.getCoins() + 10);
        Game.sleep();

        Main.PrintSeperator(); // --------------------------------------

        System.out.println("START THE GAME!");
        this.gameStarted = true;
        MusicPlayer.PlayRandomOfType("gameStart");
        Game.sleep((int)(MusicPlayer.GetCurrentSongTime() / 1000)); // Sleep for as long as the Round Start song takes + 5000
        MusicPlayer.PlayRandomOfType("game");

        Main.PrintSeperator(); // --------------------------------------

        this.PrintTurns();
        // Run game after doing starting logic
        this.Run();
    }

    private void CPUPlayerLogic() {
        Player currentPlayer = this.players[this.current_player];

        // Scan for Computer Player Items
        Item[] items = currentPlayer.getItems();
        boolean itemExists = false;
        List<Integer> useableItemsIdx = new ArrayList<Integer>();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                useableItemsIdx.add(i);
                itemExists = true;
            }
        }

        if (itemExists) {
            double d = Math.random();
            // 20% chance of using an item or 100% chance of using an item if they already
            // have 3 of them
            if (d < 0.2 || useableItemsIdx.size() >= 3) {
                // Randomly pick an item
                int itemIdx = useableItemsIdx.get(Main.getRandomNumber(0, useableItemsIdx.size()));

                if (items[itemIdx] instanceof BooBell) {
                    // CPU Logic for Boo Bell
                    // Randomly pick a target and swap with them.
                    HashMap<String, String> options = new HashMap<String, String>();

                    int randomTargetIdx = Main.getRandomNumber(0, this.players.length);
                    while (randomTargetIdx == this.current_player && this.players.length > 1) {
                        randomTargetIdx = Main.getRandomNumber(0, this.players.length);
                    }

                    if (currentPlayer.getCoins() >= 50 && this.players[randomTargetIdx].getStars() > 0) {
                        options.put("steal", "star");
                        this.players[this.current_player].useItem(itemIdx, this.players[randomTargetIdx], options);
                    } else if (currentPlayer.getCoins() >= 5 && this.players[randomTargetIdx].getCoins() > 0) {
                        options.put("steal", "coins");
                        this.players[this.current_player].useItem(itemIdx, this.players[randomTargetIdx], options);
                    }
                } else if (items[itemIdx] instanceof WrapBlock) {
                    // CPU Logic for Wrap Block
                    // Randomly pick a target and swap with them.
                    int randomTargetIdx = Main.getRandomNumber(0, this.players.length);
                    while (randomTargetIdx == this.current_player && this.players.length > 1) {
                        randomTargetIdx = Main.getRandomNumber(0, this.players.length);
                    }
                    currentPlayer.useItem(itemIdx, this.players[randomTargetIdx]);
                } else {
                    // Use the item normally if it doesn't have any special properties
                    currentPlayer.useItem(itemIdx);
                }
                Game.sleep();
            }
        }

        // Always roll
        Dice.roll();
        SFXPlayer.PlayDiceLand();
        System.out.format("Rolled a %s!\n", Dice.getNum());
    }

    public void Run() {
        boolean isRunning = true;
        while(isRunning){
            Player currentPlayer = this.players[this.current_player];
            System.out.format("%s\'s turn!\n", currentPlayer.getName());
            Game.sleep();
            if (!currentPlayer.getIsCPU()) {
                // Choice of using menu options
                UserMenu();
            } else {
                // CPU Logic
                CPUPlayerLogic();
            }
    
            // Calculate destination and check to make sure size doesn't exceed map size (if
            // so, then loop back)
            int destination = currentPlayer.getCurrentPosition() + Dice.getNum();
            if (destination >= this.map.getMapSize()) {
                destination = destination - this.map.getMapSize();
    
                System.out.format("%s passed start! Got 10 coins!\n", currentPlayer.getName());
                currentPlayer.setCoins(currentPlayer.getCoins() + 10);
            }
    
            currentPlayer.setCurrentPosition(destination);
    
            // Land the player on the space and activate that space's effect
            this.map.land(currentPlayer.getCurrentPosition(), currentPlayer);
            Game.sleep();
    
            // Move to the next player. If current player outside of array, then decrease
            // turn and reset current_player
            this.current_player++;
            if (this.current_player >= this.players.length) {
                Game.turns -= 1;
                if (Game.turns > 0) {
                    this.PrintTurns();
                    this.current_player = 0;
                }
            }
    
            // Once the game ends
            if (Game.turns == 0) {
                Main.PrintSeperator();
                System.out.println("THAT'S THE GAME!");
                Main.PrintSeperator();
                Game.sleep();
                this.PrintRankings(true);
                Main.PrintSeperator();
                MusicPlayer.PlayRandomOfType("gameEnd");
                System.out.println("Thank you for playing!");
                isRunning = false;
                Main.prompt("", false);
            }
        }
    }

    public void PrintTurns() {
        System.out.println("--------------------------------");
        if(Game.turns == 5)
            System.out.format("          FINAL %s TURNS        \n", Game.turns);
        else
            System.out.format("              Turn %s           \n", Game.turns);
        
        System.out.println("--------------------------------");
    }

    public static int getTurns(){ return Game.turns; }
    public Map getMap(){ return this.map; }
    public int getCurrentPlayer(){ return this.current_player; }
    public boolean getGameStarted(){ return this.gameStarted; }
    public Player[] getPlayers(){ return this.players; }

    public void setCurrentPlayer(int current_player){ this.current_player = current_player; }
    public void setGameStarted(boolean gameStarted){ this.gameStarted = gameStarted; }
}
