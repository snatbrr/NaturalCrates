package me.snat.naturalcrates.command;

import me.snat.naturalcrates.NaturalCrates;
import me.snat.naturalcrates.type.PhysicalCrate;
import me.snat.naturalcrates.type.VirtualCrate;
import me.snat.naturalcrates.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
                sender.sendMessage("\n" + format("&9&l") + parse("Crate Commands:"), parse(" /crate create") + format("&b") + parse(" create a crate"), parse(" /crate remove") + format("&b") + parse(" remove a crate"), parse(" /crate key") + format("&b") + parse(" give the player a key for a crate"), parse(" /crate set") + format("&b") + parse(" set a block to a crate"), parse(" /crate list") + format("&b") + parse(" list all the crates"), "\n");
                return false;
            }


            switch (args[0]) {

                case "create" -> {

                    String name = args[1];
                    if (args.length != 2) {
                        sender.sendMessage(getServerMessage("&c/nc create <crate name> <display name>"));
                        return false;
                    }

                    if (main.getCrateManager().getCrateConfig().get(name) != null) {
                        sender.sendMessage(getServerMessage("&cA crate with that name already exists"));
                        return false;
                    }

                    main.getCrateManager().getVirtualCrates().put(name, new VirtualCrate(name));
                    main.getCrateManager().saveCratesName();
                    sender.sendMessage(getServerMessage("&aCrate &b" + name + " &awas successfully created!"));
                    return false;
                }

                case "set" -> {

                    String name = args[1];
                    Block block = player.getTargetBlock(null, 5);

                    if (args.length != 2) {
                        sender.sendMessage(getServerMessage("&c/nc set <crate>"));
                        return false;
                    }

                    if (block.getType() == Material.AIR) {
                        sender.sendMessage(getServerMessage("&cYou must be looking at a block"));
                        return false;
                    }

                    if (main.getCrateManager().getVirtualCrates().get(name) == null) {
                        sender.sendMessage(getServerMessage("&cA crate with that name does not exist"));
                        return false;
                    }

                    main.getCrateManager().placeCrate(block.getLocation(), main.getCrateManager().getVirtualCrates().get(name));
                    main.getCrateManager().saveCratesLocation(name);
                }

                case "list" -> {

                    if (args.length != 1) {
                        sender.sendMessage(getServerMessage("&c/nc list"));
                        return false;
                    }

                    sender.sendMessage(format("&9&l" + parse("Crates:")));
                    for (VirtualCrate crate : main.getCrateManager().getVirtualCrates().values()) {
                        sender.sendMessage(parse(format(" - &b")) + crate.getName());
                    }

                    return false;
                }


                case "delete" -> {

                    if (args.length != 2) {
                        sender.sendMessage(getServerMessage("&c/nc delete <crate>"));
                        return false;
                    }

                    if (main.getCrateManager().getCrateConfig().get(args[1]) == null) {
                        sender.sendMessage(getServerMessage("&cThere is no such crate!"));
                        return false;
                    }

                    if (main.getCrateManager().getCrateConfig().getString(args[1] + ".location") == null) {
                        main.getCrateManager().getCrateConfig().set(args[1], null);
                        main.getCrateManager().getVirtualCrates().remove(args[1]);
                        main.getCrateManager().saveCratesName();
                        sender.sendMessage(getServerMessage("&aCrate &b" + args[1] + " &awas successfully deleted!"));
                        return false;
                    }

                    Location location = new Location(
                            Bukkit.getWorld(main.getCrateManager().getCrateConfig().getString(args[1] + ".location" + ".world")),
                            main.getCrateManager().getCrateConfig().getDouble(args[1] + ".location" + ".x"),
                            main.getCrateManager().getCrateConfig().getDouble(args[1] + ".location" + ".y"),
                            main.getCrateManager().getCrateConfig().getDouble(args[1] + ".location" + ".z")
                    );

                    main.getCrateManager().removeCrate(location, args[1]);
                    main.getCrateManager().saveCratesName();
                    sender.sendMessage(getServerMessage("&aCrate &b" + args[1] + " &awas successfully deleted!"));
                    return false;
                }

                case "key" -> {


                    if (args.length != 3) {
                        sender.sendMessage(ChatUtils.getServerMessage("&c/nc key <give> <crate>"));
                        return false;
                    }

                    String key = args[2];
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatUtils.getServerMessage("&cThat player is not online!"));
                        return false;
                    }

                    if (main.getCrateManager().getCrateConfig().getConfigurationSection("crates").getKeys(false) == null) {
                        sender.sendMessage(ChatUtils.getServerMessage("&cThat crate does not exist!"));
                        return false;
                    }


                    ItemStack itemstack = new ItemStack(Material.TRIPWIRE_HOOK);
                    ItemMeta meta = itemstack.getItemMeta();
                    meta.setDisplayName(format(key));
                    itemstack.setItemMeta(meta);
                    target.getInventory().addItem(itemstack);


                }

            }

        } else {
            sender.sendMessage(ChatUtils.format("Only players can use this command!"));
        }

        return false;
    }
}
