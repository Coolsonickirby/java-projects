/*|----------------------------------------------------------------|*
 *| CIS-171 Java Programming                                       |*
 *| Assignment #9 - Big Dice Game                                  |*
 *| Written By: Ali Hussain (Coolsonickirby/Random)                |*
 *|----------------------------------------------------------------|*
 */

package com.game.nus3audio;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.io.*;

import com.game.Main;
import com.game.Utils;

public class nus3audio {
    public final boolean IS_DEBUG = Main.IS_DEBUG; // Change Main.IS_DEBUG to true/false
    public final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

    public class FileStorage {
        public int fileOffset;
        public int fileSize;

        FileStorage(int fileOffset, int fileSize) {
            this.fileOffset = fileOffset;
            this.fileSize = fileSize;
        }
    }

    public class FileEntry {
        public String toneName;
        public byte[] fileData;

        FileEntry(String toneName, byte[] fileData) {
            this.toneName = toneName;
            this.fileData = fileData;
        }
    }

    public abstract class Entry {
        public String magic; // char[4] usually
        public int size; // UInt32

        public void BaseRead(InputStream inputStream, int magicLength) {
            try {
                this.magic = Utils.ReadString(inputStream, magicLength);
                this.size = Utils.ReadUInt32(inputStream, BYTE_ORDER);
            } catch (Exception e) {
                if (IS_DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        public void Read(InputStream inputStream) {}
    }

    public class HEADER extends Entry {
        // Magic = NUS3
        @Override
        public void Read(InputStream inputStream) {
            this.BaseRead(inputStream, 4);
        }
    }

    public class AUDIINDX extends Entry {
        // Magic = AUDIINDX
        public int count;

        @Override
        public void Read(InputStream inputStream) {
            try {
                this.BaseRead(inputStream, 8);
                this.count = Utils.ReadUInt32(inputStream, BYTE_ORDER);
            } catch (Exception e) {
                if (IS_DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class TNID extends Entry { // TNID = Tone ID
        // Magic = TNID
        int[] trackNumbers;
        byte[] data;

        @Override
        public void Read(InputStream inputStream) {
            this.Read(inputStream, 0);
        }

        public void Read(InputStream inputStream, int AudiindxCount){
            this.BaseRead(inputStream, 4);
            if(this.size >= AudiindxCount * 4){
                this.trackNumbers = new int[AudiindxCount];
                for(int i = 0; i < AudiindxCount; i++){
                    this.trackNumbers[i] = Utils.ReadUInt32(inputStream, BYTE_ORDER);
                }
            }else {
                try {
                    this.data = new byte[size];
                    inputStream.read(this.data);
                } catch (Exception e) {
                    if(IS_DEBUG){ e.printStackTrace(); }
                }
            }
        }
    }

    public class NMOF extends Entry { // NMOF = Name Of File?
        int[] names;
        byte[] data;

        @Override
        public void Read(InputStream inputStream) {
            this.Read(inputStream, 0);
        }

        public void Read(InputStream inputStream, int AudiindxCount) {
            this.BaseRead(inputStream, 4);
            if (this.size >= AudiindxCount * 4) {

                this.names = new int[AudiindxCount];
                for (int i = 0; i < AudiindxCount; i++) {
                    this.names[i] = Utils.ReadUInt32(inputStream, BYTE_ORDER);
                }

            } else {
                this.data = new byte[this.size];
                try {
                    inputStream.read(this.data);
                } catch (Exception e) {
                    if (IS_DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class ADOF extends Entry { // ADOF = ?
        // Magic = ADOF
        public FileStorage[] fileEntries;
        byte[] data;

        @Override
        public void Read(InputStream inputStream) {
            this.Read(inputStream, 0);
        }

        public void Read(InputStream inputStream, int AudiindxCount) {
            this.BaseRead(inputStream, 4);
            if (this.size >= AudiindxCount * 4) {
                this.fileEntries = new FileStorage[AudiindxCount];
                for (int i = 0; i < AudiindxCount; i++) {
                    this.fileEntries[i] = new FileStorage(
                            // New File Storage Entry
                            Utils.ReadUInt32(inputStream, BYTE_ORDER), // File Offset
                            Utils.ReadUInt32(inputStream, BYTE_ORDER) // File Size
                    );
                }
            } else {
                this.data = new byte[this.size];
                try {
                    inputStream.read(this.data);
                } catch (Exception e) {
                    if (IS_DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class TNNM extends Entry { // TNNM = Tone Name
        // Magic = TNNM
        public ArrayList<String> stringSection;

        @Override
        public void Read(InputStream inputStream) {
            this.BaseRead(inputStream, 4);
            this.stringSection = new ArrayList<String>();
            String toneName = "";
            for (int i = 0; i < this.size; i++) {
                try {
                    int res = inputStream.read();
                    if (res != 0) {
                        toneName += (char) res;
                    } else {
                        this.stringSection.add(toneName);
                        toneName = "";
                    }
                } catch (Exception e) {
                    if (IS_DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class JUNK extends Entry { // JUNK = Junk
        // Magic = JUNK
        byte[] padding;

        @Override
        public void Read(InputStream inputStream) {
            this.BaseRead(inputStream, 4);
            padding = new byte[this.size];
            try {
                inputStream.read(padding);
            } catch (Exception e) {
                if (IS_DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class PACK extends Entry { // PACK = PACK?
        // Magic = PACK
        @Override
        public void Read(InputStream inputStream) {
            this.BaseRead(inputStream, 4);
        }
    }

    public HEADER head;
    public AUDIINDX audi;
    public TNID tnid;
    public NMOF nmof;
    public ADOF adof;
    public TNNM tnnm;
    public JUNK junk;
    public PACK pack;

    private ArrayList<FileEntry> files = new ArrayList<FileEntry>();

    public nus3audio(){}

    public nus3audio(String filePath) {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath))); 
            this.Read(inputStream);
        } catch (Exception e) {
            if (IS_DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public void Read(InputStream inputStream){
        inputStream.mark(Integer.MAX_VALUE);
        this.Reset();
        //#region Read Everything
        this.head.Read(inputStream);
        this.audi.Read(inputStream);
        this.tnid.Read(inputStream, this.audi.count);
        this.nmof.Read(inputStream, this.audi.count);
        this.adof.Read(inputStream, this.audi.count);
        this.tnnm.Read(inputStream);
        this.junk.Read(inputStream);
        this.pack.Read(inputStream);
        //#endregion
        try {
            for(int i = 0; i < adof.fileEntries.length; i++){
                inputStream.reset();

                // Less accurate - JDK 8 compatible
                inputStream.skip(adof.fileEntries[i].fileOffset);
                byte[] data = new byte[adof.fileEntries[i].fileSize];
                inputStream.read(data);
                this.files.add(new FileEntry(this.tnnm.stringSection.get(i), data));
                
                // More accurate - JDK 11 or higher only
                // inputStream.skipNBytes(adof.fileEntries[i].fileOffset); // 
                // this.files.add(new FileEntry(this.tnnm.stringSection.get(i), inputStream.readNBytes(adof.fileEntries[i].fileSize)));
            }
            inputStream.close();
        } catch (Exception e) {
            if(IS_DEBUG){ e.printStackTrace(); }
        }
    }

    public void PrintEntries(){
        for(int i = 0; i < this.files.size(); i++){
            System.out.format("ID: %s - Name: %s - Size: %s\n", i, this.files.get(i).toneName, this.files.get(i).fileData.length);
        }
    }

    public FileEntry GetByToneName(String toneName){
        return this.files.stream().filter(entry -> entry.toneName.equals(toneName)).findFirst().orElse(null);
    }

    public void Reset() {
        head = new HEADER();
        audi = new AUDIINDX();
        tnid = new TNID();
        nmof = new NMOF();
        adof = new ADOF();
        tnnm = new TNNM();
        junk = new JUNK();
        pack = new PACK();
        files = new ArrayList<FileEntry>();
    }
}
