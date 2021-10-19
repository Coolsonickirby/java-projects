package com.game.Audio;

public class MusicEntry {
    private int loopStart;
    private int loopEnd;
    private String type;
    private String filePath;

    MusicEntry(int loopStart, int loopEnd, String type, String filePath) {
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.type = type;
        this.filePath = filePath;
    }

    MusicEntry(String info) {
        String[] infoArr = info.split(" ", 4);
        this.loopStart = Integer.parseInt(infoArr[0]);
        this.loopEnd = Integer.parseInt(infoArr[1]);
        this.type = infoArr[2];
        this.filePath = infoArr[3];
    }

    public int getLoopStart(){ return this.loopStart; }
    public int getLoopEnd(){ return this.loopEnd; }
    public String getType(){ return this.type; }
    public String getFilePath(){ return this.filePath; }
}
