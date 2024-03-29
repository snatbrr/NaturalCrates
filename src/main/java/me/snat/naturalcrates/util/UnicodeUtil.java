package me.snat.naturalcrates.util;

public class UnicodeUtil {

    public static String parse(String string) {
        return string.toUpperCase()
                .replace("Q", "q")
                .replace("W", "ᴡ")
                .replace("E", "ᴇ")
                .replace("R", "ʀ")
                .replace("T", "ᴛ")
                .replace("Y", "ʏ")
                .replace("U", "ᴜ")
                .replace("I", "ɪ")
                .replace("O", "ᴏ")
                .replace("P", "ᴘ")
                .replace("A", "ᴀ")
                .replace("S", "s")
                .replace("D", "ᴅ")
                .replace("F", "ꜰ")
                .replace("G", "ɢ")
                .replace("H", "ʜ")
                .replace("J", "ᴊ")
                .replace("K", "ᴋ")
                .replace("L", "ʟ")
                .replace("Z", "ᴢ")
                .replace("X", "x")
                .replace("C", "ᴄ")
                .replace("V", "ᴠ")
                .replace("B", "ʙ")
                .replace("N", "ɴ")
                .replace("M", "ᴍ");
    }



}
