package de.bloodeko.towns.cmds.settings;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;

// setting can be set
// setting can be read
// setting can be loaded
// setting can be disabled
public class Settings {
    public static final Flag<Location> WARP_FLAG = new WarpFlag("warp");
    
}
