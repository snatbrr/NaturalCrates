package me.snat.naturalcrates.manager;

import me.snat.naturalcrates.NaturalCrates;
import me.snat.naturalcrates.type.PhysicalCrate;
import me.snat.naturalcrates.type.VirtualCrate;
import me.snat.naturalcrates.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class CrateManager {

    private NaturalCrates plugin;
    private File file;
    private YamlConfiguration crateConfig;

    private HashMap<Location, PhysicalCrate> physicalCrates = new HashMap<>();
    private HashMap<String, VirtualCrate> virtualCrates = new HashMap<>();


    public CrateManager(NaturalCrates plugin) {
        this.physicalCrates = new HashMap<>();
        this.virtualCrates = new HashMap<>();
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "crates.yml");
        this.crateConfig = YamlConfiguration.loadConfiguration(file);


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

    public void saveCratesLocation(String name) {
        for (Location location : physicalCrates.keySet()) {
            PhysicalCrate crate = physicalCrates.get(location);
            crateConfig.set(name + ".location" + ".world", location.getWorld().getName());
            crateConfig.set(name + ".location" + ".x", location.getX());
            crateConfig.set(name + ".location" + ".y", location.getY());
            crateConfig.set(name + ".location" + ".z", location.getZ());
        }
        saveFile();
    }

    public void saveCratesName() {
        for (String name : virtualCrates.keySet()) {
            VirtualCrate crate = virtualCrates.get(name);
            crateConfig.set(crate.getName() + ".name", crate.getName());
        }
        saveFile();
    }



    public void removeCrate(Location location, String crate) {
        PhysicalCrate physicalCrate = physicalCrates.get(location);
        physicalCrates.remove(location);
        for (ArmorStand armorStand : Bukkit.getWorld(getCrateConfig().getString(crate + ".location.world")).getEntitiesByClass(ArmorStand.class)) {
            if (armorStand.getCustomName() != null && armorStand.getCustomName().equals(crate)) {
                armorStand.remove();
            }
        }
        virtualCrates.remove(crate);
        crateConfig.set(crate, null);
        saveFile();
    }


    public void loadCratesLocation() {
        for (String crateName : crateConfig.getKeys(false)) {

            String world = (crateConfig.get(crateName + ".world")).toString();
            double x = crateConfig.getDouble(crateName + ".x");
            double y = crateConfig.getDouble(crateName + ".y");
            double z = crateConfig.getDouble(crateName + ".z");
            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            VirtualCrate crate = new VirtualCrate(crateName);
            physicalCrates.put(location, new PhysicalCrate(crate, location, null));
        }
    }

    public void loadCratesName() {
        for (String crateName : crateConfig.getKeys(false)) {
            VirtualCrate crate = new VirtualCrate(crateName);
            virtualCrates.put(crateName, crate);
        }
    }


    public YamlConfiguration getCrateConfig() { return crateConfig; }

    public HashMap<Location, PhysicalCrate> getPhysicalCrates() { return physicalCrates; }

    public HashMap<String, VirtualCrate> getVirtualCrates() { return virtualCrates; }
}

