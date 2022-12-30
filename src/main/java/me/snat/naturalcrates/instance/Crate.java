package me.snat.naturalcrates.instance;

import me.snat.naturalcrates.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

import static me.snat.naturalcrates.Main.getPlugin;

public class Crate {

    private Main main;

    private ArmorStand as;
    private List<String> rewards;
    private String name;

    public Crate(Main main) {
        this.main = main;
        this.rewards = new ArrayList<>();
    }

    public void place(String name, Block block) {
        double x = block.getX();
        double y = block.getY();
        double z = block.getZ();
        String world = block.getWorld().toString();

        getPlugin().getCrateManager().getFile().set(name + ".cords" + ".world", world);
        getPlugin().getCrateManager().getFile().set(name + ".cords" + ".x", x);
        getPlugin().getCrateManager().getFile().set(name + ".cords" + ".y", y);
        getPlugin().getCrateManager().getFile().set(name + ".cords" + ".z", z);
        getPlugin().getCrateManager().saveFile();
    }

    public ArmorStand getAS() {
        return as;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public String getName(String name) {
        return getPlugin().getCrateManager().getFile().get(name + ".name").toString();
    }

    public String setName(String name) {
        this.name = name;
        getPlugin().getCrateManager().getFile().set(name + ".name", name);
        getPlugin().getCrateManager().saveFile();
        return name;
    }

    public void setReward(String name, String reward) {
        rewards.add(reward);
        getPlugin().getCrateManager().getFile().set(name + ".rewards", rewards);
        getPlugin().getCrateManager().saveFile();
    }
}
