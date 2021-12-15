/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Audio;

import javax.sound.sampled.*;

import com.game.App;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MusicPlayer {
    private static ArrayList<MusicEntry> musicEntries;
    private static int current_song_index;
    private static Clip musicSource;
    private static boolean isReady = false;
    private static float volume = 1f;

    public static void InitalSetup() {
        try {
            MusicPlayer.musicSource = AudioSystem.getClip();
            MusicPlayer.isReady = true;
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    // Implement Fade Out and Fade In
    public static void SetVolume(float volume){
        if(volume > 1f){ MusicPlayer.volume = 1f; }
        else if(volume < 0f){ MusicPlayer.volume = 0f; }
        else{ MusicPlayer.volume = volume; }

        if(MusicPlayer.musicSource.isActive()){
            FloatControl volControl = (FloatControl)MusicPlayer.musicSource.getControl(FloatControl.Type.MASTER_GAIN);
            volControl.setValue(20f * (float) Math.log10(MusicPlayer.volume));
        }
    }

    public static void PlayMusic() {
        try {
            if(!MusicPlayer.isReady){ return; }
            MusicEntry currentEntry = MusicPlayer.musicEntries.get(MusicPlayer.current_song_index);

            // Stop and close it in case something is already playing
            MusicPlayer.musicSource.stop();
            MusicPlayer.musicSource.close(); 
            
            // Code partially borrowed from https://web.archive.org/web/20200224053637/http://www.javazoom.net/mp3spi/documents.html for mp3spi and vorbisspi setup
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(currentEntry.getFilePath()));
            AudioInputStream din = null;
            AudioFormat baseFormat = inputStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
            		AudioFormat.Encoding.PCM_SIGNED,
            		baseFormat.getSampleRate(),
            		16,
            		baseFormat.getChannels(),
            		baseFormat.getChannels() * 2,
            		baseFormat.getSampleRate(),
            		false
            		);
            din = AudioSystem.getAudioInputStream(decodedFormat, inputStream);
            MusicPlayer.musicSource.open(din);

            if(App.IS_DEBUG){ System.out.format("[MusicPlayer::PlayMusic] Now playing %s.....\n", (new File(currentEntry.getFilePath())).getName()); }

            MusicPlayer.musicSource.start();

            if(currentEntry.getLoopStart() > 0 && currentEntry.getLoopEnd() > 0){
                MusicPlayer.musicSource.setLoopPoints(currentEntry.getLoopStart(), currentEntry.getLoopEnd());
                MusicPlayer.musicSource.loop(Clip.LOOP_CONTINUOUSLY);
            }


            FloatControl volControl = (FloatControl)MusicPlayer.musicSource.getControl(FloatControl.Type.MASTER_GAIN);
            volControl.setValue(20f * (float) Math.log10(MusicPlayer.volume));
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static void PlayMusic(int idx){
        MusicPlayer.current_song_index = idx;
        MusicPlayer.PlayMusic();
    }

    public static void PauseMusic(){
        try {
            if(!MusicPlayer.isReady){ return; }
    
            MusicPlayer.musicSource.stop();
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static void ResumeMusic(){
        try {
            if(!MusicPlayer.isReady){ return; }
    
            MusicEntry currentEntry = MusicPlayer.musicEntries.get(MusicPlayer.current_song_index);

            MusicPlayer.musicSource.start();

            if(currentEntry.getLoopStart() > 0 && currentEntry.getLoopEnd() > 0){
                MusicPlayer.musicSource.setLoopPoints(currentEntry.getLoopStart(), currentEntry.getLoopEnd());
                MusicPlayer.musicSource.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static boolean LoadPlaylist(String path){
        try {
            List<String> entries = Files.readAllLines(Paths.get(path)).stream().filter(entry -> entry != "").collect(Collectors.toList());
            MusicPlayer.musicEntries = new ArrayList<MusicEntry>();
            for(int i = 0; i < entries.size(); i++){ MusicPlayer.musicEntries.add(new MusicEntry(entries.get(i))); }
            return true;
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
        return false;
    }

    public static long GetCurrentSongTime(){
        try {
            if(!MusicPlayer.isReady){ return 0; }

            return MusicPlayer.musicSource.getMicrosecondLength();
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
        return 0;
    }
}