/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Final Project - Flappy Bird FX                                 |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Utils {
    // #region Write Methods
    public static void WriteUInt8(OutputStream outputStream, int val) {
        try {
            outputStream.write(val);
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::WriteUInt8] Failed writing UInt8 to output stream!");
        }
    }

    public static void WriteUInt32(OutputStream outputStream, int val, ByteOrder byteOrder) {
        try {
            outputStream.write(ByteBuffer.allocate(4).putInt(val).order(byteOrder).array());
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::WriteUInt32] Failed writing UInt32 to output stream!");
        }
    }

    public static void WriteFloat(OutputStream outputStream, float val, ByteOrder byteOrder) {
        try {
            outputStream.write(ByteBuffer.allocate(4).putFloat(val).order(byteOrder).array());
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::WriteUInt32] Failed writing UInt32 to output stream!");
        }
    }

    public static void WriteString(OutputStream outputStream, String str) {
        try {
            for (int i = 0; i < str.length(); i++)
                outputStream.write(str.charAt(i));
        } catch (IOException e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::WriteString] Failed writing string to output stream!");
        }
    }
    // #endregion

    // #region Read Methods
    public static int ReadUInt8(InputStream inputStream) {
        try {
            return inputStream.read();
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::ReadUInt8] Failed reading UInt8 from input stream!");
        }
        return -1;
    }

    public static int ReadUInt32(InputStream inputStream, ByteOrder byteOrder) {
        try {
            // byte[] res = new byte[4];
            // inputStream.read(res);
            // return ByteBuffer.wrap(res).order(byteOrder).getInt();
            return ByteBuffer.wrap(inputStream.readNBytes(4)).order(byteOrder).getInt(); // -- JDK 11 or higher
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::ReadUInt32] Failed reading UInt32 from input stream!");
        }
        return -1;
    }

    public static float ReadFloat(InputStream inputStream, ByteOrder byteOrder) {
        try {
            // byte[] res = new byte[4];
            // inputStream.read(res);
            // return ByteBuffer.wrap(res).order(byteOrder).getFloat();
            return ByteBuffer.wrap(inputStream.readNBytes(4)).order(byteOrder).getFloat(); // -- JDK 11 or higher
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::ReadUInt32] Failed reading UInt32 from input stream!");
        }
        return -1;
    }

    public static String ReadString(InputStream inputStream, int length) {
        try {
            byte[] res = new byte[length];
            inputStream.read(res);
            return new String(res, StandardCharsets.UTF_8);
            // return new String(inputStream.readNBytes(length), StandardCharsets.UTF_8); --
            // JDK 11 or higher
        } catch (Exception e) {
            if (App.IS_DEBUG) {
                e.printStackTrace();
            }
            System.out.println("[Utils::ReadString] Failed reading string from input stream!");
        }
        return "";
    }
    // #endregion
}
