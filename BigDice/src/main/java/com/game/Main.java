/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|                                                                |*
 *| Notes: Mario Party on the command line! This is a very bare    |*
 *| bones version of Mario Party, which includes the following:    |*
 *| - Human Players                                                |*
 *| - CPU Players                                                  |*
 *| - 4 Type of Spaces (Blue, Red, Item, and Star)                 |*
 *| - 3 Type of Items (Mushroom, Wrap Block, Boo Bell)             |*
 *| - Random Map Generation                                        |*
 *| - Music Player (Formats: wav, ogg, mp3, au, aiff)              |*
 *| - nus3audio support (Format from Super Smash Bros. Ultimate    |*
 *|   and Gundam Versus)                                           |*
 *| - Save and Load game support (optional save editor on          |*
 *|   http://coolsonickirby.com/mpcli)                             |*
 *|                                                                |*
 *|                                                                |*
 *| Third-Party Libraries Used:                                    |*
 *| - jLayer (mp3spi and vorbisspi dep)                            |*
 *| - jogg (vorbisspi dep)                                         |*
 *| - jorbis (vorbisspi dep)                                       |*
 *| - tritonus (mp3spi and vorbisspi dep)                          |*
 *| - vorbisspi (ogg support)                                      |*
 *| - mp3spi (mp3 support)                                         |*
 *|----------------------------------------------------------------|*
 */
package com.game;

import java.util.*;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.game.Audio.*;
import java.io.*;

public class Main {
    public static final boolean IS_DEBUG = new File("debug").exists();
    public static final float MUSIC_VOL = 0.5f; // Range: 0f - 1f
    public static final float SFX_VOL = 1f; // Range: 0f - 1f

    public static Scanner reader = new Scanner(System.in); // Create globally so main and prompt can reference it
    public static final String[] names = new String[] {
            "John", // Some Random Name
            "William", // Some Random Name
            "Mario", // Super Mario Bros.
            "Luigi", // Super Mario Bros.
            "Sonic", // Sonic The Hedgehog
            "Shadow", // Sonic Adventure 2
            "Kirby", // Kirby's Dream Land
            "Yagami", // Judgement / Judge Eyes
            "Kaito", // Judgement / Judge Eyes
            "Kiryu", // Yakuza / Ryu Ga Gotoku (Like a Dragon)
            "Kazuma", // Yakuza / Ryu Ga Gotoku (Like a Dragon)
            "Ruby", // RWBY
            "Ragna", // BlazBlue
            "Yu", // Persona 4
            "Hyde", // Under Night In-Birth
            "Rindo", // NEO: The World Ends With You
            "Neku", // The World Ends With You
            "Sora" // Kingdom Hearts (SUPER SMASH BRUDDAS HYPEEEEEEEEE)
    };
    public static List<Integer> usedNames = new ArrayList<>();
    public static final int MIN_PLAYERS = 2; // Game will work with only 1 player, but that'd be boring.
    public static final int MAX_PLAYERS = 4; // Game will work with more than 4 players, but Mario Party only allows 4.

    public static void main(String[] args) {
        PrintCredits();
        
        if(IS_DEBUG){ System.out.println("[Main] Debug Mode is active! Remember to turn it off for production release!"); }

        //#region Audio Setup
        String audioChoice = choice("Would you like to enable music and SFX (Sound Effects)? (Y|N) ", "(Y|N)", true);
        if(audioChoice.equals("Y")){
            SFXPlayer.InitalSetup();
            SFXPlayer.SetVolume(SFX_VOL);
            
            String playlist = prompt("Enter playlist file (Default will be playlist.txt): ", true);
            
            playlist = playlist.trim().equals("") ? "playlist.txt" : playlist;

            if((new File(playlist).exists())){
                MusicPlayer.InitalSetup();
                MusicPlayer.SetVolume(MUSIC_VOL);
                if(!MusicPlayer.LoadPlaylist(playlist)){ System.out.format("Failed loading playlist %s!\n", playlist); }
                else{ MusicPlayer.PlayRandomOfType("menu"); }
            }

        }
        PrintSeperator();
        //#endregion

        String choice = Main.choice("Would you like to start a new game or load a game state? (N|L) ", "(N|L)", true);
        PrintSeperator();
        if(choice.equals("L"))
            LoadGame();
        else
            NewGame();
    }

    public static String getUniqueName() {
        int nameIdx = getRandomNumber(0, names.length);
        while (usedNames.contains(nameIdx) && usedNames.size() < names.length) {
            nameIdx = getRandomNumber(0, names.length);
        }
        usedNames.add(nameIdx);
        return names[nameIdx];
    }
    
    public static void PrintCredits() {
        PrintSeperator();
        System.out.println("|----------------------- Mario Party CLI ------------------------|");
        System.out.println("|----------------------------------------------------------------|");
        System.out.println("|- Credits: -----------------------------------------------------|");
        System.out.println("|---------------------------- Main ------------------------------|");
        System.out.println("|- Ali Hussain (Coolsonickirby/Random) | Program Writer ---------|");
        System.out.println("|- Nintendo / Hudson Soft -------------| Mario Party ------------|");
        System.out.println("|-------------------------- Libraries ---------------------------|");
        System.out.println("|- javazoom ---------------------------| jLayer, mp3 + vorbis spi|");
        System.out.println("|- jcracft ----------------------------| jOgg, jOrbis -----------|");
        System.out.println("|- Matthias Pfisterer & Florian Bomers | Tritonus ---------------|");
        System.out.println("|---------------------------- Names -----------------------------|");
        System.out.println("|- Nintendo, SEGA, RGG Studios, HAL ---| A.I. Character Names ---|");
        System.out.println("|- Rooster Teeth, Arc System Works ----| A.I. Character Names ---|");
        System.out.println("|- Atlus, French Bread, Square Enix. --| A.I. Character Names ---|");
        System.out.println("|------------------------ Sound Effects -------------------------|");
        System.out.println("|- Nintendo ---------------------------| Save (SFX) -------------|");
        System.out.println("|- Nintendo ---------------------------| Star Get (SFX) ---------|");
        System.out.println("|- Nintendo ---------------------------| Boo (SFX) --------------|");
        System.out.println("|- SoundEffectsFactory ----------------| Dice Land (SFX) --------|");
        System.out.println("|- Alan McKinney ----------------------| Wrap Block (SFX) -------|");
        PrintSeperator();
    }

    public static void LoadGame(){
        String filePath = prompt("Enter the game state file path: ", true);
        File file = new File(filePath);
        while(!file.exists()){
            System.out.format("%s does not exist! Please double check your path.\n", filePath);
            SFXPlayer.PlayInvalidChoice();
            Game.sleep(1000);
            filePath = prompt("Enter the game state file path: ", true);
            file = new File(filePath);
        }

        Game loadedGame = GameState.loadGameState(filePath);

        if(loadedGame == null){
            System.out.println("Failed loading game state! Will start a new game.");
            NewGame();
            return;
        }

        if(loadedGame.getGameStarted()){
            loadedGame.PrintTurns();
            MusicPlayer.PlayRandomOfType("game");
            loadedGame.Run();
        }else
            loadedGame.Start();
    }

    public static void NewGame(){
        int totalPlayers = 0;
        int humanPlayers = Integer.parseInt(choice("How many human players are playing? ", "\\d+", true));
        int computerPlayers = Integer.parseInt(choice("How many computer players are playing? ", "\\d+", true));
        totalPlayers = humanPlayers + computerPlayers;

        while (totalPlayers < MIN_PLAYERS || totalPlayers > MAX_PLAYERS) {
            if (totalPlayers < MIN_PLAYERS)
                System.out.format("Total amount of players cannot be less than %S!\n", MIN_PLAYERS);
            else
                System.out.format("Total amount of players cannot surpass %s!\n", MAX_PLAYERS);

            SFXPlayer.PlayInvalidChoice();

            humanPlayers = Integer.parseInt(choice("How many human players are playing? ", "\\d+", true));
            computerPlayers = Integer.parseInt(choice("How many computer players are playing? ", "\\d+", true));
            totalPlayers = humanPlayers + computerPlayers;
        }

        int gameTurns = Integer.parseInt(choice("How many turns are going to be played in this round? ", "\\d+", true));
        while (gameTurns <= 0) {
            System.out.println("You need at least one turn!");
            SFXPlayer.PlayInvalidChoice();
            Game.sleep(1000);
            gameTurns = Integer.parseInt(choice("How many turns are going to be played in this round? ", "\\d+", true));
        }

        int mapSize = Integer.parseInt(choice("How big is the map going to be in this round? ", "\\d+", true));
        while (mapSize < 10) {
            System.out.println("You need at least 10 spots on the map!");
            SFXPlayer.PlayInvalidChoice();
            Game.sleep(1000);
            mapSize = Integer.parseInt(choice("How big is the map going to be in this round? ", "\\d+", true));
        }

        Player[] players = new Player[totalPlayers];

        // Fill up all human players
        for (int i = 0; i < humanPlayers; i++)
            players[i] = new Player(prompt(String.format("Enter Player %s name: ", i + 1), true), false);

        // Fill up the rest with computers
        for (int i = humanPlayers; i < totalPlayers; i++)
            players[i] = new Player(getUniqueName(), true);

        Game mainGame = new Game(players, mapSize, gameTurns);
        mainGame.Start();
    }

    public static String prompt(String prompt, boolean PlaySFX) { // Emulate Python prompt function
        System.out.print(prompt);
        if(PlaySFX){ SFXPlayer.PlayMenuPrompt(); }
        return reader.nextLine();
    }



    public static String choice(String prompt, String patternString, boolean PlaySFX) {
        Pattern p = Pattern.compile(patternString);
        String choice = prompt(prompt, PlaySFX);

        while (!p.matcher(choice).matches()) {
            System.out.println("Invalid choice!");
            SFXPlayer.PlayInvalidChoice();
            Game.sleep(1000);
            choice = prompt(prompt, PlaySFX);
        }

        return choice;
    }

    public static void PrintSeperator() {
        System.out.println("------------------------------------------------------------------");
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
      }
}