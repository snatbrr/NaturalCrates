package me.snat.naturalcrates.types;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
public class VirtualCrate {

    private String name;
    private List<ItemStack> rewards;
    private ItemStack key;

    public VirtualCrate(String name) {
        this.rewards = new ArrayList<>();
        this.name = name;
    }

}
