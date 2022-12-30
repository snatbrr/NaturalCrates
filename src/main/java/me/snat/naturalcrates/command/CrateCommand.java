package me.snat.naturalcrates.command;

import me.snat.naturalcrates.NaturalCrates;
import me.snat.naturalcrates.types.PhysicalCrate;
import me.snat.naturalcrates.types.VirtualCrate;
import me.snat.naturalcrates.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.snat.naturalcrates.util.ChatUtils.format;
import static me.snat.naturalcrates.util.ChatUtils.getServerMessage;
import static me.snat.naturalcrates.util.UnicodeUtil.parse;

public class CrateCommand implements CommandExecutor {

    private final NaturalCrates main;

    public CrateCommand(NaturalCrates main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                //sender.sendMessage("&9&l" + parse("\nCrate Commands:"), parse(" /crate create") + cc("&b") + parse(" create a crate"), parse(" /crate remove") + cc("&b") + parse(" remove a crate"), parse(" /crate key") + cc("&b") + parse(" give the player a key for a crate"), parse(" /crate set") + cc("&b") + parse(" set a block to a crate"), parse(" /crate list") + cc("&b") + parse(" list all the crates"), "\n");
                return false;
            }


            switch (args[0]) {

                case "create" -> {

                    String name = args[1];
                    if (args.length != 2) {
                        sender.sendMessage(getServerMessage("&c/nc create <crate name> <display name>"));
                        return false;
                    }

                    if (main.getCrateManager().getVirtualCrates().get(name) != null) {
                        sender.sendMessage(getServerMessage("&cA crate with that name already exists"));
                        return false;
                    }

                    Block block = player.getTargetBlock(null, 5);
                    main.getCrateManager().getVirtualCrates().put(name, new VirtualCrate(name));
                    sender.sendMessage(getServerMessage("&aCrate &b" + name + " &awas successfully created!"));
                    return false;
                }

                case "set" -> {

                    if (args.length != 2) {
                        sender.sendMessage(getServerMessage("&c/nc set <crate>"));
                        return false;
                    }

                    String crateName = args[1];

                    if (main.getCrateManager().getVirtualCrates().get(crateName) == null) {
                        sender.sendMessage("&cThere is no such crate!");
                        return false;
                    }

                    main.getCrateManager().placeCrate(player.getTargetBlock(null, 5).getLocation(), main.getCrateManager().getVirtualCrates().get(crateName));

                }

                case "list" -> {


                    sender.sendMessage(getServerMessage("&9&l" + parse("\nCrates:")));
                    for (VirtualCrate crate : main.getCrateManager().getVirtualCrates().values()) {
                        sender.sendMessage(parse(format(" - &b")) + crate.getName());
                    }
                    return false;
                }


                case "delete" -> {

                    if (main.getCrateManager().getVirtualCrates().get(args[1]) == null) {
                        sender.sendMessage(getServerMessage("&cThere is no such crate!"));
                        return false;
                    }

                    main.getCrateManager().getVirtualCrates().remove(args[1]);
                    for (PhysicalCrate crate : main.getCrateManager().getPhysicalCrates().values()) {
                        if (crate.getVirtualCrate().getName().equals(args[1])) {
                            crate.getArmorStand().remove();
                            main.getCrateManager().getPhysicalCrates().remove(crate);
                            main.getCrateManager().getVirtualCrates().remove(args[1]);
                        }
                    }

                    sender.sendMessage(getServerMessage("&aCrate &b" + args[1] + " &awas successfully deleted!"));

                    break;
                }

                case "key" -> {

                    sender.sendMessage("This command is not yet implemented");

                    if (args.length != 3) {
                        sender.sendMessage(ChatUtils.getServerMessage("&c/nc key <give> <crate>"));
                        return false;
                    }

                    String crateKey = args[2];
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatUtils.getServerMessage("&cThat player is not online!"));
                        return false;
                    }

                    // I don't know what the fuck your on about with this
                    /*
                    if (cratesFile.get(crateKey) == null) {
                        sendMessage(player, prefix + "&cThere is no such crate!");
                        return false;
                    }

                     */


                    ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                    ItemMeta keyMeta = key.getItemMeta();
                    keyMeta.setDisplayName(format(crateKey));
                    key.setItemMeta(keyMeta);
                    target.getInventory().addItem(key);


                }

            }

        } else {
            sender.sendMessage(ChatUtils.format("Only players can use this command!"));
        }

        return false;
    }
}
