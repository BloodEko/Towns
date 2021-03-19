package de.bloodeko.towns.util;

import java.util.UUID;

import org.bukkit.Bukkit;

public class Players {
    
    @SuppressWarnings("deprecation")
    public static UUID get(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }
    
}
