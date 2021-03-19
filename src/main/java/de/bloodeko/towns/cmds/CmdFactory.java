package de.bloodeko.towns.cmds;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.cmds.general.BuilderCmd;
import de.bloodeko.towns.cmds.general.ClaimCmd;
import de.bloodeko.towns.cmds.general.DeleteCmd;
import de.bloodeko.towns.cmds.general.ExtensionCmd;
import de.bloodeko.towns.cmds.general.ExtensionsCmd;
import de.bloodeko.towns.cmds.general.FoundCmd;
import de.bloodeko.towns.cmds.general.GovenorCmd;
import de.bloodeko.towns.cmds.general.InfoCmd;
import de.bloodeko.towns.cmds.general.MapCmd;
import de.bloodeko.towns.cmds.general.MapCmd.MapClickHandler;
import de.bloodeko.towns.cmds.general.RenameCmd;
import de.bloodeko.towns.cmds.general.StageCmd;
import de.bloodeko.towns.cmds.general.TpCmd;
import de.bloodeko.towns.cmds.general.UnclaimCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.DamageAnimalsCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.PvpCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.WarpCmd;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownCmd;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCreateCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotExpropriateCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotInfoCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotLeaveCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotListCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotNameCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotRemoveCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotRentCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotRentoutCmd;
import de.bloodeko.towns.util.Messages;

public class CmdFactory {
    
    public static void init(Towns plugin) {
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd cmd = new TownCmd(handler);
        plugin.getCommand("town").setExecutor(cmd);
        plugin.getCommand("town").setTabCompleter(cmd);
        
        PlotCmd plotCmd = newPlotCmd(plugin.getChunkMap());
        plugin.getCommand("plot").setExecutor(plotCmd);
        plugin.getCommand("plot").setTabCompleter(plotCmd);
    }
    
    public static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        ChunkMap map = plugin.getChunkMap();
        TownRegistry towns = plugin.getTownRegistry();
        SettingsRegistry settings = plugin.getSettingsRegistry();
        
        put(cmds, "map", new MapCmd(map, newClickHandler(plugin)));
        put(cmds, "info", new InfoCmd(map, settings));
        put(cmds, "tp", new TpCmd(map, towns));
        
        put(cmds, "claim", new ClaimCmd(map));
        put(cmds, "unclaim", new UnclaimCmd(map));
        put(cmds, "rename", new RenameCmd(map, towns));
        
        put(cmds, "builder", new BuilderCmd(map));
        put(cmds, "governor", new GovenorCmd(map));
        put(cmds, "found", new FoundCmd(map, towns));
        put(cmds, "delete", new DeleteCmd(map, towns));
        
        put(cmds, "stage", new StageCmd(map));
        put(cmds, "warp", new WarpCmd(map));
        put(cmds, "pvp", new PvpCmd(map, "PvP"));
        put(cmds, "damageAnimals", new DamageAnimalsCmd(map, "DamageAnimals"));
        
        put(cmds, "extensions", new ExtensionsCmd(map, settings));
        put(cmds, "extension", new ExtensionCmd(map, settings));
        put(cmds, "plot", newPlotCmd(map));
        
        return new CmdHandler(cmds);
    }
    
    public static void put(Map<String, CmdBase> cmds, String id, CmdBase base) {
        cmds.put(Messages.get("cmds." + id), base);
    }
    
    public static PlotCmd newPlotCmd(ChunkMap map) {
        Map<String, CmdBase> cmds = new HashMap<>();
        put(cmds, "plot.create", new PlotCreateCmd(map));
        put(cmds, "plot.expropriate", new PlotExpropriateCmd(map));
        put(cmds, "plot.info", new PlotInfoCmd(map));
        put(cmds, "plot.leave", new PlotLeaveCmd(map));
        put(cmds, "plot.list", new PlotListCmd(map));
        put(cmds, "plot.name", new PlotNameCmd(map));
        put(cmds, "plot.remove", new PlotRemoveCmd(map));
        put(cmds, "plot.rent", new PlotRentCmd(map));
        put(cmds, "plot.rentout", new PlotRentoutCmd(map));
        return new PlotCmd(map, new CmdHandler(cmds));
    }
    
    public static MapClickHandler newClickHandler(Towns plugin) {
        MapClickHandler handler = new MapClickHandler(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
}
