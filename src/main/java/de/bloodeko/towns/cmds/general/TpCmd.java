package de.bloodeko.towns.cmds.general;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.util.Messages;

public class TpCmd extends CmdBase {
    private TownRegistry registry;

    public TpCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.tp.cmdUsage");
            return;
        }
        Town town = registry.get(args[0]);
        Location loc = (Location) town.getSettings().readSetting(Settings.WARP);
        if (loc == null) {
            Messages.say(player, "cmds.tp.warpNotSet");
        } else {
            player.teleport(loc);
            Messages.say(player, "cmds.tp.teleport", town.getSettings().getName());
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        String name = args.length == 0 ? "" : args[0];
        return registry.getTabCompletion(name);
    }

}
