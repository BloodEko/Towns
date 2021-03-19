package de.bloodeko.towns.cmds.general;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.Settings;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegistry;

public class TpCmd extends CmdBase {
    private TownRegistry registry;

    public TpCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("Use /town tp <name> to teleport.");
            return;
        }
        Town town = registry.get(args[0]);
        Location loc = town.getSettings().getSettingValue(Settings.WARP_FLAG);
        if (loc == null) {
            player.sendMessage("Warp point is not set.");
        } else {
            player.teleport(loc);
            player.sendMessage("Teleporting to " + town.getSettings().getName() + "...");
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        String name = args.length == 0 ? "" : args[0];
        return registry.getTabCompletion(name);
    }

}
