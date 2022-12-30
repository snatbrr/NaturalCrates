package me.snat.naturalcrates.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import static me.snat.naturalcrates.util.UnicodeUtil.parse;

public class CC {

    public static String cc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(Player player, String ...strings) {
        for (String string : strings) {
            player.sendMessage(cc(string));
        }
    }

    public static String prefix = parse("&bNC &7» ");
}
