/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.Audio;

public class MusicEntry {
    private static final String DELIMITER = "<split>";
    private int loopStart;
    private int loopEnd;
    private String title;
    private String filePath;

    MusicEntry(int loopStart, int loopEnd, String title, String filePath) {
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.title = title;
        this.filePath = filePath;
    }

    MusicEntry(String info) {
        String[] infoArr = info.split(DELIMITER, 4);
        this.loopStart = Integer.parseInt(infoArr[0].strip());
        this.loopEnd = Integer.parseInt(infoArr[1].strip());
        this.title = infoArr[2].strip();
        this.filePath = infoArr[3].strip();
    }

    public int getLoopStart(){ return this.loopStart; }
    public int getLoopEnd(){ return this.loopEnd; }
    public String getTitle(){ return this.title; }
    public String getFilePath(){ return this.filePath; }
}
