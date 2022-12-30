package me.snat.naturalcrates.listener;


import me.snat.naturalcrates.NaturalCrates;
import me.snat.naturalcrates.types.PhysicalCrate;
import me.snat.naturalcrates.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.snat.naturalcrates.util.ChatUtils.getServerMessage;
import static me.snat.naturalcrates.util.ChatUtils.prefix;

public class CrateListener implements Listener {

    private NaturalCrates plugin;

    public CrateListener(NaturalCrates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        Player player = e.getPlayer();
        PhysicalCrate physicalCrate;

        if (plugin.getCrateManager().getPhysicalCrates().get(e.getClickedBlock().getLocation()) != null) {
            physicalCrate = plugin.getCrateManager().getPhysicalCrates().get(e.getClickedBlock().getLocation());

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
                player.sendMessage(prefix + "You have opened a crate!");


            }


            if (e.getItem().equals(plugin.getCrateManager().getPhysicalCrates().get(e.getClickedBlock().getLocation()).getVirtualCrate().getKey())) {
                e.setCancelled(true);
                List<ItemStack> rewards = physicalCrate.getVirtualCrate().getRewards();
                int random = (int) (Math.random() * rewards.size());
                ItemStack reward = rewards.get(random);
                player.getInventory().addItem(reward);
                player.sendMessage(ChatUtils.getServerMessage("&aYou have received a " + reward));
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

            } else {
                player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(1));
                player.sendMessage(getServerMessage("&cYou do not have a key for this crate!"));
            }

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (plugin.getCrateManager().getPhysicalCrates().get(e.getBlock().getLocation()) != null) {
            e.setCancelled(true);
            player.sendMessage(getServerMessage("&cYou cannot break this crate!"));
        }

    }

}
