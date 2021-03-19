package de.bloodeko.towns.session1;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session2.Town;

public class InfoCmd extends TownCmdBase {
    
    public InfoCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        printInfo(getTown(player), player);
    }
    
    public void printInfo(Town town, Player player) {
        if (town == null) {
            player.sendMessage("There is no town at your location.");
            return;
        }
        player.sendMessage("--- Town Info ---");
        player.sendMessage("Id: " + town.getId());
        player.sendMessage("Name: " + town.getName());
        player.sendMessage("Size: " + town.getSize());
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }
}
