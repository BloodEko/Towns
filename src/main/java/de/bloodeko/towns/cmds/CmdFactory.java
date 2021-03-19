package de.bloodeko.towns.cmds;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.cmds.general.ClaimCmd;
import de.bloodeko.towns.cmds.general.InfoCmd;
import de.bloodeko.towns.cmds.general.MapCmd;
import de.bloodeko.towns.cmds.general.MapCmd.MapClickHandler;
import de.bloodeko.towns.cmds.general.NameCmd;
import de.bloodeko.towns.cmds.general.TpCmd;
import de.bloodeko.towns.cmds.general.UnclaimCmd;
import de.bloodeko.towns.cmds.general.WarpCmd;
import de.bloodeko.towns.cmds.settings.AnimalsCmd;
import de.bloodeko.towns.cmds.settings.BuildingCmd;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownCmd;

public class CmdFactory {

    
    public static void init(Towns plugin) {
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd listener = new TownCmd(handler);
        plugin.getCommand("town").setExecutor(listener);
        plugin.getCommand("town").setTabCompleter(listener);
    }
    
    public static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        CmdHandler handler = new CmdHandler(cmds);
        ChunkMap map = plugin.getChunkMap();
        cmds.put("map", new MapCmd(map, newClickHandler(plugin)));
        cmds.put("claim", new ClaimCmd(map));
        cmds.put("unclaim", new UnclaimCmd(map));
        cmds.put("info", new InfoCmd(map));
        cmds.put("name", new NameCmd(map, plugin.getTownRegistry()));
        cmds.put("tp", new TpCmd(map, plugin.getTownRegistry()));
        cmds.put("warp", new WarpCmd(map));
        registerSettings(map, cmds);
        return handler;
    }
    
    public static MapClickHandler newClickHandler(Towns plugin) {
        MapClickHandler handler = new MapClickHandler(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
    
    public static void registerSettings(ChunkMap map, Map<String, CmdBase> cmds) {
        cmds.put("building", new BuildingCmd(map, "Building"));
        cmds.put("killing", new AnimalsCmd(map, "AnimalProtect"));
    }
}
