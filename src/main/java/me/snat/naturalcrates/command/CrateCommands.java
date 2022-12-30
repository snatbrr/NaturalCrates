package me.snat.naturalcrates.command;

import me.snat.naturalcrates.Main;
import me.snat.naturalcrates.instance.Crate;
import me.snat.naturalcrates.util.UnicodeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static me.snat.naturalcrates.Main.getPlugin;
import static me.snat.naturalcrates.util.CC.*;
import static me.snat.naturalcrates.util.UnicodeUtil.parse;

public class CrateCommands implements CommandExecutor {

    private Main main;
    private Crate crate;

    private YamlConfiguration cratesFile;

    public CrateCommands(Main main, Crate crate) {
        this.main = main;
        this.crate = crate;
        this.cratesFile = getPlugin().getCrateManager().getFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        ArmorStand as = crate.getAS();


        if (!(sender instanceof Player)) {
            System.out.println("You must be a player to use this command");
            return false;
        }

        if (args.length == 0) {
            sendMessage(player, "&9&l" + parse("\nCrate Commands:"),
                    parse(" /crate create") + cc("&b") + parse(" create a crate"),
                    parse(" /crate remove") + cc("&b") + parse(" remove a crate"),
                    parse(" /crate key") + cc("&b") + parse(" give the player a key for a crate"),
                    parse(" /crate set") + cc("&b") + parse(" set a block to a crate"),
                    parse(" /crate list") + cc("&b") + parse(" list all the crates"),
                    "\n");
            return false;
        }


        switch (args[0]) {

            case "create" -> {

                String name = cc(args[1]);

                if (args.length != 2) {
                    sendMessage(player, prefix + "&c/nc create <crate name> <display name>");
                    return false;
                }

                if (cratesFile.get(name) != null) {
                    sendMessage(player, prefix + "&cThere is already a crate called this!");
                    return false;
                }

                cratesFile.set(name + ".key", cc(name));
                crate.setName(args[1]);
                crate.setReward(name, "DIAMOND");
                crate.setReward(name, "IRON_INGOT");

                sendMessage(player, prefix + "&aCrate " + "&b" + name + " &awas successfully created!");
                break;
            }

            case "set" -> {

                if (args.length < 2 || args.length > 2) {
                    sendMessage(player, prefix + "&c/nc set <crate>");
                    return false;
                }

                String crateName = cc(args[1]);

                if (cratesFile.get(crateName) == null) {
                    sendMessage(player, prefix + "&cThere is no such crate!");
                    return false;
                }

                Block block = player.getTargetBlock(null, 5);
                Location loc = block.getLocation();
                crate.place(crateName, block);

                //spawn the armor stand in the middle of the block
                as = player.getWorld().spawn(loc.add(0.5, -0.5, 0.5), ArmorStand.class);
                as.setGravity(false);
                as.setVisible(false);
                as.setInvulnerable(true);
                as.setCustomNameVisible(true);
                as.setCustomName(cc(crateName));
                as.setCollidable(false);
                break;
            }

            case "list" -> {

                if (args.length < 1 || args.length > 1) {
                    sendMessage(player, prefix + "&c/nc list");
                    return false;
                }

                if (cratesFile.getKeys(false).isEmpty()) {
                    sendMessage(player, prefix + "&cThere are no crates!");
                    return false;
                }

                for (String crate : cratesFile.getKeys(false)) {
                    sendMessage(player, prefix + "&a" + crate);
                }
                break;
            }


            case "delete" -> {

                if (args.length < 2 || args.length > 2) {
                    sendMessage(player, prefix + "&c/nc delete <crate>");
                    return false;
                }

                String crate = cc(args[1]);

                if (cratesFile.get(crate) == null) {
                    sendMessage(player, prefix + "&cThere is no such crate!");
                    return false;
                }

                for (ArmorStand armorStand : Bukkit.getWorld("world").getEntitiesByClass(ArmorStand.class)) {
                    if (armorStand.getCustomName() != null && armorStand.getCustomName().equals(crate)) {
                        armorStand.remove();
                    }
                }


                sendMessage(player, prefix + "&aSuccessfully deleted &c" + cratesFile.get(crate + ".name") + "&a.");
                cratesFile.set(crate, null);
                getPlugin().getCrateManager().saveFile();

                break;
            }

            case "key" -> {

                if (args.length < 3 || args.length > 3) {
                    sendMessage(player, prefix + "&c/nc key <give> <crate>");
                    return false;
                }

                String crateKey = cc(args[2]);
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sendMessage(player, prefix + "&cThat player is not online!");
                    return false;
                }

                if (cratesFile.get(crateKey) == null) {
                    sendMessage(player, prefix + "&cThere is no such crate!");
                    return false;
                }

                ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta keyMeta = key.getItemMeta();
                keyMeta.setDisplayName(cc(crateKey));
                key.setItemMeta(keyMeta);
                target.getInventory().addItem(key);
                break;
            }


        }


        return false;
    }
}
