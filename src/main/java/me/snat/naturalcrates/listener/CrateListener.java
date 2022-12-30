package me.snat.naturalcrates.listener;


import me.snat.naturalcrates.Main;
import me.snat.naturalcrates.instance.Crate;
import me.snat.naturalcrates.manager.CrateManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.snat.naturalcrates.Main.getPlugin;
import static me.snat.naturalcrates.util.CC.prefix;
import static me.snat.naturalcrates.util.CC.sendMessage;

public class CrateListener implements Listener {

    private Crate crate;
    private Main main;

    private YamlConfiguration cratesFile;

    public CrateListener(Main main, Crate crate) {
        this.crate = crate;
        this.main = main;
        this.cratesFile = getPlugin().getCrateManager().getFile();
    }


    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getClickedBlock() == null) return;

        //loop through the crates.yml file and check if the block the player clicks is a crate
        for (String crateName : cratesFile.getKeys(false)) {
            String world = cratesFile.getString(crateName + ".cords.world");
            double x = cratesFile.getDouble(crateName + ".cords.x");
            double y = cratesFile.getDouble(crateName + ".cords.y");
            double z = cratesFile.getDouble(crateName + ".cords.z");

            Location loc = new Location(player.getWorld(), x, y, z);


            if (e.getClickedBlock().getLocation().equals(loc)) {
                for (String keyName : cratesFile.getKeys(false)) {
                    String key = cratesFile.getString(keyName + ".key");
                    if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(key) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        //give the player a random item from the rewards list in crates.yml
                        List<String> rewards = cratesFile.getStringList(crateName + ".rewards");
                        int random = (int) (Math.random() * rewards.size());
                        String reward = rewards.get(random);
                        ItemStack item = new ItemStack(Material.valueOf(reward));
                        player.getInventory().addItem(item);
                        sendMessage(player, prefix + "&aYou have received a " + reward);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    } else {
                        if (player.getInventory().getItemInMainHand().getType().isAir() || player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) {
                            sendMessage(player, prefix + "&cYou do not have a key for this crate");
                            player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(1));
                        }
                    }
                }
            }
        }


    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        for (String crateName : cratesFile.getKeys(false)) {
            String world = cratesFile.getString(crateName + ".cords.world");
            double x = cratesFile.getDouble(crateName + ".cords.x");
            double y = cratesFile.getDouble(crateName + ".cords.y");
            double z = cratesFile.getDouble(crateName + ".cords.z");

            Location loc = new Location(player.getWorld(), x, y, z);

            if (e.getBlock().getLocation().equals(loc)) {
                e.setCancelled(true);
                sendMessage(player, prefix + "&cYou cannot destroy a crate!");
            }
        }
    }

}
