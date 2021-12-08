// This file is mostly based off the video made by CoderBoi - https://www.youtube.com/watch?v=3lSzfidowTE
package com.game;

import java.time.Duration;
import java.time.Instant;

public class FPS {
    private FPS() {}

    private static Duration fpsDeltaTime = Duration.ZERO;
    private static Duration lastTime = Duration.ZERO;
    private static Instant beginTime = Instant.now();
    private static double deltatime = fpsDeltaTime.toMillis() - lastTime.toMillis();

    public static void calcBeginTime(){
        beginTime = Instant.now();
        fpsDeltaTime = Duration.ZERO;
    }

    public static void calcDeltaTime(){
        fpsDeltaTime = Duration.between(beginTime, Instant.now());
        deltatime = (double)fpsDeltaTime.toMillis() - lastTime.toMillis();
        lastTime = fpsDeltaTime;
    }

    public static double getDeltaTime(){
        return deltatime / 1000;
    }
}
