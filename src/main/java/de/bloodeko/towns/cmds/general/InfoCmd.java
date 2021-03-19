package de.bloodeko.towns.cmds.general;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class InfoCmd extends CmdBase {
    
    public InfoCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        printInfo(getTownAsPlayer(player), player);
    }

    public void printInfo(Town town, Player player) {
        player.sendMessage("--- Town info ---");
        player.sendMessage("Name: "  + town.getName() + "(" + town.getId() + ")");
        player.sendMessage("Size: "  + town.getArea().getSize());
        player.sendMessage("Owner: " + Bukkit.getOfflinePlayer(town.getOwner()).getName());
        
        player.sendMessage("-- Area --");
        player.sendMessage("minX: " + town.getArea().getMinX());
        player.sendMessage("minZ: " + town.getArea().getMinZ());
        player.sendMessage("maxX: " + town.getArea().getMaxX());
        player.sendMessage("maxZ: " + town.getArea().getMaxZ());
        
        player.sendMessage("-- Settings --");
        player.sendMessage("Warp:   " + String.valueOf(town.getWarp() != null));
        player.sendMessage("Building: " + town.getSettings().canBuild());
        player.sendMessage("Killing: " + town.getSettings().canKillAnimals());
    }
}
