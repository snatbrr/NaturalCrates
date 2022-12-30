package me.snat.naturalcrates.types;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

@Data
public class PhysicalCrate {

    private VirtualCrate virtualCrate;
    private Location location;
    private ArmorStand armorStand;

    public PhysicalCrate(VirtualCrate virtualCrate, Location location, ArmorStand armorStand) {
        this.virtualCrate = virtualCrate;
        this.location = location;
        this.armorStand = armorStand;
    }

}
