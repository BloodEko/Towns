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
import de.bloodeko.towns.cmds.general.PlotCmd;
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
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.util.Messages;

public class CmdFactory {
    
    public static void init(Towns plugin) {
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd cmd = new TownCmd(handler);
        plugin.getCommand("town").setExecutor(cmd);
        plugin.getCommand("town").setTabCompleter(cmd);
    }
    
    public static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        ChunkMap map = plugin.getChunkMap();
        TownRegistry registry = plugin.getTownRegistry();
        
        put(cmds, "map", new MapCmd(map, newClickHandler(plugin)));
        put(cmds, "info", new InfoCmd(map));
        put(cmds, "tp", new TpCmd(map, registry));
        
        put(cmds, "claim", new ClaimCmd(map));
        put(cmds, "unclaim", new UnclaimCmd(map));
        put(cmds, "rename", new RenameCmd(map, registry));
        
        put(cmds, "builder", new BuilderCmd(map));
        put(cmds, "governor", new GovenorCmd(map));
        put(cmds, "found", new FoundCmd(map, registry));
        put(cmds, "delete", new DeleteCmd(map, registry));
        
        put(cmds, "stage", new StageCmd(map));
        put(cmds, "warp", new WarpCmd(map));
        put(cmds, "pvp", new PvpCmd(map, "PvP"));
        put(cmds, "damageAnimals", new DamageAnimalsCmd(map, "DamageAnimals"));
        
        put(cmds, "extensions", new ExtensionsCmd(map, newSettingsRegistry()));
        put(cmds, "extension", new ExtensionCmd(map, newSettingsRegistry()));
        put(cmds, "plot", new PlotCmd(map));
        
        return new CmdHandler(cmds);
    }
    
    public static void put(Map<String, CmdBase> cmds, String id, CmdBase base) {
        cmds.put(Messages.get("cmds." + id), base);
    }
    
    public static SettingsRegistry newSettingsRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        registry.register(Settings.DAMAGE_ANIMALS);
        registry.register(Settings.PVP);
        registry.register(Settings.WARP);
        return registry;
    }
    
    public static MapClickHandler newClickHandler(Towns plugin) {
        MapClickHandler handler = new MapClickHandler(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
}
