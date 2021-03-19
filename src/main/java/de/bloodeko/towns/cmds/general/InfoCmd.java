package de.bloodeko.towns.cmds.general;

import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.TownSetting;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownPeople;

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
        player.sendMessage("Name: "  + town.getSettings().getName() + "(" + town.getId() + ")");
        player.sendMessage("Stage: " + town.getSettings().getState());
        player.sendMessage("Size: "  + town.getArea().getSize());
        
        player.sendMessage("-- People --");
        player.sendMessage("Governors: " + getGovernors(town.getPeople()));
        player.sendMessage("Builders: " + getBuilders(town.getPeople()));
        
        player.sendMessage("-- Area --");
        player.sendMessage("minX: " + town.getArea().getSides().minX);
        player.sendMessage("minZ: " + town.getArea().getSides().minZ);
        player.sendMessage("maxX: " + town.getArea().getSides().maxX);
        player.sendMessage("maxZ: " + town.getArea().getSides().maxZ);
        
        player.sendMessage("-- Settings --");
        for (Entry<TownSetting, Object> entry : town.getSettings().getSettings().entrySet()) {
            player.sendMessage(entry.getKey().getName() + ": " + entry.getValue());
        }
    }
    
    private String getGovernors(TownPeople people) {
        StringJoiner joiner = new StringJoiner(", ");
        for (UUID uuid : people.getGovernors()) {
            if (people.isOwner(uuid)) {
                joiner.add(withPrefix(uuid, true));
            } else {
                joiner.add(withPrefix(uuid, false));
            }
        }
        return joiner.toString();
    }
    
    private String getBuilders(TownPeople people) {
        StringJoiner joiner = new StringJoiner(", ");
        for (UUID uuid : people.getBuilders()) {
            joiner.add(withPrefix(uuid, false));
        }
        return joiner.toString();
    }
    
    private String withPrefix(UUID uuid, boolean bold) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        String val = player.getName();
        if (val == null) {
            val = player.getUniqueId().toString();
        }
        val += ChatColor.RESET;
        if (bold) {
            val = ChatColor.BOLD + val;
        }
        if (player.isOnline()) {
            val = ChatColor.GREEN + val;
        }
        return val;
    }
}
