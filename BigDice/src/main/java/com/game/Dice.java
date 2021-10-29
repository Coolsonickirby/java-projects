/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;

public class Dice {
    private static final int MIN = 1;
    private static final int MAX = 10;
    private static int num = Main.getRandomNumber(Dice.MIN, Dice.MAX);

    public static void roll() {
        Dice.num = Main.getRandomNumber(Dice.MIN, Dice.MAX);
    }

    public static int getNum() {
        return Dice.num;
    }
}
