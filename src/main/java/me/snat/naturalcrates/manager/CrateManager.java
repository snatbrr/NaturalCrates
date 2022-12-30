package me.snat.naturalcrates.manager;

import me.snat.naturalcrates.NaturalCrates;
import me.snat.naturalcrates.types.PhysicalCrate;
import me.snat.naturalcrates.types.VirtualCrate;
import me.snat.naturalcrates.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CrateManager {

    private NaturalCrates plugin;
    private File file = new File(plugin.getDataFolder(), "crates.yml");
    private YamlConfiguration crateConfig;

    private HashMap<String, VirtualCrate> virtualCrates = new HashMap<>();
    private HashMap<Location, PhysicalCrate> physicalCrates = new HashMap<>();

    public CrateManager(NaturalCrates plugin) {
        this.plugin = plugin;

        if (!file.exists()) {
            try {
                file.createNewFile();
                crateConfig = YamlConfiguration.loadConfiguration(file);
            } catch (IOException e) {
                System.out.println("Could not create crates.yml");
            }
        }
    }

    public void saveFile() {
        try {
            crateConfig.save(file);
        } catch (IOException e) {
            System.out.println("Could not save crates.yml");
        }
    }

    public void placeCrate(Location location, VirtualCrate crate) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(ChatUtils.format(crate.getName()));
        armorStand.setCollidable(false);
        physicalCrates.put(location, new PhysicalCrate(crate, location, armorStand));

    }

    public YamlConfiguration getCrateConfig() { return crateConfig; }
    public HashMap<Location, PhysicalCrate> getPhysicalCrates() { return physicalCrates; }
    public HashMap<String, VirtualCrate> getVirtualCrates() { return virtualCrates; }
}
