package me.snat.naturalcrates;

import me.snat.naturalcrates.command.CrateCommand;
import me.snat.naturalcrates.listener.CrateListener;
import me.snat.naturalcrates.manager.CrateManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NaturalCrates extends JavaPlugin {

    private static NaturalCrates plugin;
    private CrateManager crateManager;

    @Override
    public void onEnable() {
        plugin = this;
        crateManager = new CrateManager(this);

        getCommand("crate").setExecutor(new CrateCommand(this));
        getServer().getPluginManager().registerEvents(new CrateListener(this), this);

    }

    public CrateManager getCrateManager() { return crateManager; }
}
