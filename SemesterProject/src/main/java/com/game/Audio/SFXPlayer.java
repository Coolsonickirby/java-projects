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
import java.util.HashMap;
import com.game.nus3audio.*;

public class SFXPlayer {
    private static boolean isReady = false;
    private static float volume = 1f;

    private static HashMap<SFXEnum, Clip> SFX_LIST;

    public static void InitalSetup() {
        try {
            nus3audio nus3sfx = new nus3audio();
            nus3sfx.Read(new BufferedInputStream(SFXPlayer.class.getResourceAsStream("sfx.nus3audio")));
            SFX_LIST = new HashMap<SFXEnum, Clip>();
            
            for(SFXEnum sfx_enum : SFXEnum.values()){
                try {
                    SFX_LIST.put(sfx_enum, SetupClip(new ByteArrayInputStream(nus3sfx.GetByToneName(sfx_enum.name()).fileData), sfx_enum));
                } catch (Exception e) {
                    if(App.IS_DEBUG){
                        e.printStackTrace();
                        System.out.println(sfx_enum.name() + " is not in the nus3audio!");
                    }
                }
            }

            SFXPlayer.isReady = SFX_LIST.size() > 0;
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static void PlayClip(byte[] audio){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audio));
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static Clip SetupClip(InputStream inputStream, SFXEnum enum1){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }

        return null;
    }

    public static void SetVolume(float volume){
        if(!SFXPlayer.isReady){ return; }

        if(volume > 1f){ SFXPlayer.volume = 1f; }
        else if(volume < 0f){ SFXPlayer.volume = 0f; }
        else{ SFXPlayer.volume = volume; }

        FloatControl volControl;
        for (Clip clip : SFXPlayer.SFX_LIST.values()) {
            try {
                if(clip.isActive()){
                    volControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                    volControl.setValue(20f * (float) Math.log10(SFXPlayer.volume));
                }
            } catch (Exception e) {
                if(App.IS_DEBUG){ e.printStackTrace(); }
            }
        }
    }

    public static void PlaySFX(SFXEnum type, int loop){
        try {
            if(!SFXPlayer.isReady){ return; }
            // for (Clip clip : SFX_LIST.values()) { clip.stop(); }
            // Get Clip object from the HashMap
            Clip clipToPlay = SFX_LIST.get(type);
            
            // Stop it, reset the position to 0, then play it
            clipToPlay.stop();
            clipToPlay.setMicrosecondPosition(0);
            clipToPlay.start();
            clipToPlay.loop(loop);

            FloatControl volControl = (FloatControl)clipToPlay.getControl(FloatControl.Type.MASTER_GAIN);
            volControl.setValue(20f * (float) Math.log10(SFXPlayer.volume));
        } catch (Exception e) {
            if(App.IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public static void PlaySFXEnum(SFXEnum type){ SFXPlayer.PlaySFX(type, 0); }
}
