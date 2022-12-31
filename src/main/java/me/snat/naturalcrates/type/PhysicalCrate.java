package me.snat.naturalcrates.type;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class PhysicalCrate {

    private VirtualCrate virtualCrate;
    private Location location;
    private ArmorStand armorStand;

    public PhysicalCrate(VirtualCrate virtualCrate, Location location, ArmorStand armorStand) {
        this.virtualCrate = virtualCrate;
        this.location = location;
        this.armorStand = armorStand;

    }


    public Location getLocation() {  return location; }
    public ArmorStand getArmorStand() { return armorStand; }

    public VirtualCrate getVirtualCrates() { return virtualCrate; }
}
