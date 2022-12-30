package me.snat.naturalcrates.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import static me.snat.naturalcrates.util.UnicodeUtil.parse;

public class ChatUtils {

    public static String prefix = parse("&bNC &7» ");

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getServerMessage(String s) {
        return format(prefix + s);
    }

}
