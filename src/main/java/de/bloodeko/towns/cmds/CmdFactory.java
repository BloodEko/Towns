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
import de.bloodeko.towns.cmds.general.PlotCmd;
import de.bloodeko.towns.cmds.general.MapCmd.MapClickHandler;
import de.bloodeko.towns.cmds.general.RenameCmd;
import de.bloodeko.towns.cmds.general.StageCmd;
import de.bloodeko.towns.cmds.general.TpCmd;
import de.bloodeko.towns.cmds.general.UnclaimCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.DamageAnimalsCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.PvpCmd;
import de.bloodeko.towns.cmds.settings.SettingsCmds.WarpCmd;
import de.bloodeko.towns.cmds.settings.SettingsRegistry;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownCmd;
import de.bloodeko.towns.town.TownRegistry;

public class CmdFactory {
    
    public static void init(Towns plugin) {
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd listener = new TownCmd(handler);
        plugin.getCommand("town").setExecutor(listener);
        plugin.getCommand("town").setTabCompleter(listener);
    }
    
    public static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        ChunkMap map = plugin.getChunkMap();
        TownRegistry registry = plugin.getTownRegistry();
        
        cmds.put("map", new MapCmd(map, newClickHandler(plugin)));
        cmds.put("info", new InfoCmd(map));
        cmds.put("tp", new TpCmd(map, registry));
        
        cmds.put("claim", new ClaimCmd(map));
        cmds.put("unclaim", new UnclaimCmd(map));
        cmds.put("rename", new RenameCmd(map, registry));
        
        cmds.put("builder", new BuilderCmd(map));
        cmds.put("governor", new GovenorCmd(map));
        cmds.put("found", new FoundCmd(map, registry));
        cmds.put("delete", new DeleteCmd(map, registry));
        
        cmds.put("stage", new StageCmd(map));
        cmds.put("setWarp", new WarpCmd(map));
        cmds.put("pvp", new PvpCmd(map, "PvP"));
        cmds.put("damageAnimals", new DamageAnimalsCmd(map, "DamageAnimals"));
        
        cmds.put("extensions", new ExtensionsCmd(map, newSettingsRegistry()));
        cmds.put("extension", new ExtensionCmd(map, newSettingsRegistry()));
        cmds.put("plot", new PlotCmd(map));
        
        return new CmdHandler(cmds);
    }
    
    public static SettingsRegistry newSettingsRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        registry.register(SettingsRegistry.DAMAGE_ANIMALS);
        registry.register(SettingsRegistry.PVP);
        registry.register(SettingsRegistry.WARP);
        return registry;
    }
    
    public static MapClickHandler newClickHandler(Towns plugin) {
        MapClickHandler handler = new MapClickHandler(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
}
