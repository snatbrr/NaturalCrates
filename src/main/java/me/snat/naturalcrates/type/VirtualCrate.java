package me.snat.naturalcrates.type;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VirtualCrate {


    private String name;
    private List<ItemStack> rewards;
    private ItemStack key;

    public VirtualCrate(String name) {
        this.rewards = new ArrayList<>();
        this.name = name;
    }

    public String getName() { return name; }



}
