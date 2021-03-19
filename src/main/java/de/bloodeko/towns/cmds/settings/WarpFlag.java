package de.bloodeko.towns.cmds.settings;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;

public class WarpFlag extends Flag<Location> {
    public static final Flag<Location> VALUE = new WarpFlag("warp");

    protected WarpFlag(String name) {
        super(name);
    }

    @Override
    public Location parseInput(FlagContext context) throws InvalidFlagFormat {
        return null;
    }

    @Override
    public Location unmarshal(Object o) {
        return null;
    }

    @Override
    public Object marshal(Location o) {
        return null;
    }
}
