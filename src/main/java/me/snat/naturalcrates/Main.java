package me.snat.naturalcrates;

import me.snat.naturalcrates.command.CrateCommands;
import me.snat.naturalcrates.instance.Crate;
import me.snat.naturalcrates.listener.CrateListener;
import me.snat.naturalcrates.manager.CrateManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private CrateManager crateManager;

    @Override
    public void onEnable() {
        plugin = this;
        crateManager = new CrateManager(this);
        getCommand("crate").setExecutor(new CrateCommands(this, new Crate(this)));
        getServer().getPluginManager().registerEvents(new CrateListener(this, new Crate(this)), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin() {
        return plugin;
    }

    public CrateManager getCrateManager() { return crateManager; }
}
