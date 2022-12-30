package me.snat.naturalcrates.manager;

import me.snat.naturalcrates.Main;
import me.snat.naturalcrates.instance.Crate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.snat.naturalcrates.Main.getPlugin;

public class CrateManager {

    private File file = new File(getPlugin().getDataFolder(), "crates.yml");

    private YamlConfiguration crates = YamlConfiguration.loadConfiguration(file);


    public CrateManager(Main main) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.printf("Could not create crates.yml");
            }
        }
    }

    public void saveFile() {
        try {
            crates.save(file);
        } catch (IOException e) {
            System.out.println("Could not save crates.yml");
        }
    }

    public YamlConfiguration getFile() { return crates; }







}
