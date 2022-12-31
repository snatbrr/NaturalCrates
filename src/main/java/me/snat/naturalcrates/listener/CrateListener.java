package me.snat.naturalcrates.listener;
import me.snat.naturalcrates.NaturalCrates;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class CrateListener implements Listener {

    private NaturalCrates plugin;

    public CrateListener(NaturalCrates plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        Player player = e.getPlayer();





    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {


    }

}
