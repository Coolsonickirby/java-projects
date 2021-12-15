/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Audio;

public class MusicEntry {
    private int loopStart;
    private int loopEnd;
    private String filePath;

    MusicEntry(int loopStart, int loopEnd, String filePath) {
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.filePath = filePath;
    }

    MusicEntry(String info) {
        String[] infoArr = info.split(" ", 3);
        this.loopStart = Integer.parseInt(infoArr[0]);
        this.loopEnd = Integer.parseInt(infoArr[1]);
        this.filePath = infoArr[2];
    }

    public int getLoopStart(){ return this.loopStart; }
    public int getLoopEnd(){ return this.loopEnd; }
    public String getFilePath(){ return this.filePath; }
}
