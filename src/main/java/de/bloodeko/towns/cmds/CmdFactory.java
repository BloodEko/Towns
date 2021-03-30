package de.bloodeko.towns.cmds;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.cmds.core.BuilderCmd;
import de.bloodeko.towns.cmds.core.ClaimCmd;
import de.bloodeko.towns.cmds.core.DeleteCmd;
import de.bloodeko.towns.cmds.core.FoundCmd;
import de.bloodeko.towns.cmds.core.GovenorCmd;
import de.bloodeko.towns.cmds.core.InfoCmd;
import de.bloodeko.towns.cmds.core.OwnerCmd;
import de.bloodeko.towns.cmds.core.SettingsCmd;
import de.bloodeko.towns.cmds.core.TestCmd;
import de.bloodeko.towns.cmds.core.UnclaimCmd;
import de.bloodeko.towns.cmds.map.MapCmd;
import de.bloodeko.towns.cmds.map.MapFactory;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownCmd;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.cmds.ExtensionCmd;
import de.bloodeko.towns.town.settings.cmds.ExtensionsCmd;
import de.bloodeko.towns.town.settings.cmds.RenameCmd;
import de.bloodeko.towns.town.settings.cmds.StageCmd;
import de.bloodeko.towns.town.settings.cmds.TpCmd;
import de.bloodeko.towns.town.settings.general.DamageAnimalsSetting.AnimalProtectCmd;
import de.bloodeko.towns.town.settings.general.PearlSetting.PearlCmd;
import de.bloodeko.towns.town.settings.general.PvpSetting.PvpProtectCmd;
import de.bloodeko.towns.town.settings.general.SlimeSetting.SlimeCmd;
import de.bloodeko.towns.town.settings.general.WarpSetting.WarpCmd;
import de.bloodeko.towns.town.settings.general.ZombieSetting.ZombieCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotBuilderCmd;
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
import de.bloodeko.towns.town.settings.plots.cmds.PlotReserveCmd;
import de.bloodeko.towns.util.Messages;
import net.milkbowl.vault.economy.Economy;

public class CmdFactory {
    
    public static void init(Towns plugin) {
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd cmd = new TownCmd(handler);
        plugin.getCommand("town").setExecutor(cmd);
        plugin.getCommand("town").setTabCompleter(cmd);
        
        PlotCmd plotCmd = newPlotCmd(plugin.getChunkMap(), plugin.getEconomy());
        plugin.getCommand("plot").setExecutor(plotCmd);
        plugin.getCommand("plot").setTabCompleter(plotCmd);
    }
    
    public static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        ChunkMap map = plugin.getChunkMap();
        TownRegistry towns = plugin.getTownRegistry();
        SettingsRegistry settings = plugin.getSettingsRegistry();
        
        put(cmds, "map", new MapCmd(map, MapFactory.newClickHandler(plugin)));
        put(cmds, "info", new InfoCmd(map, settings));
        put(cmds, "settings", new SettingsCmd(map, settings));
        put(cmds, "test", new TestCmd(map));
        
        put(cmds, "claim", new ClaimCmd(map, plugin.getEconomy()));
        put(cmds, "unclaim", new UnclaimCmd(map));
        
        put(cmds, "builder", new BuilderCmd(map));
        put(cmds, "governor", new GovenorCmd(map));
        put(cmds, "owner", new OwnerCmd(map));
        put(cmds, "found", new FoundCmd(map, towns, plugin.getEconomy()));
        put(cmds, "delete", new DeleteCmd(map, towns));

        put(cmds, "extensions", new ExtensionsCmd(map, settings));
        put(cmds, "extension", new ExtensionCmd(map, settings));
        
        put(cmds, "tp", new TpCmd(map, towns));
        put(cmds, "rename", new RenameCmd(map, towns));
        put(cmds, "stage", new StageCmd(map));
        put(cmds, "warp", new WarpCmd(map));
        put(cmds, "pvp", new PvpProtectCmd(map, Messages.get("settings.pvp")));
        put(cmds, "damageAnimals", new AnimalProtectCmd(map, Messages.get("settings.damageAnimals")));
        put(cmds, "warp", new WarpCmd(map));
        put(cmds, "pearl", new PearlCmd(map, Messages.get("settings.pearl")));
        put(cmds, "slime", new SlimeCmd(map, Messages.get("settings.slime")));
        put(cmds, "zombie", new ZombieCmd(map, Messages.get("settings.zombie")));
        
        return new CmdHandler(cmds);
    }
    
    public static void put(Map<String, CmdBase> cmds, String id, CmdBase base) {
        cmds.put(Messages.get("cmds." + id), base);
    }
    
    public static PlotCmd newPlotCmd(ChunkMap map, Economy economy) {
        Map<String, CmdBase> cmds = new HashMap<>();
        put(cmds, "plot.builder", new PlotBuilderCmd(map));
        put(cmds, "plot.create", new PlotCreateCmd(map));
        put(cmds, "plot.expropriate", new PlotExpropriateCmd(map));
        put(cmds, "plot.info", new PlotInfoCmd(map));
        put(cmds, "plot.leave", new PlotLeaveCmd(map, economy));
        put(cmds, "plot.list", new PlotListCmd(map));
        put(cmds, "plot.name", new PlotNameCmd(map));
        put(cmds, "plot.remove", new PlotRemoveCmd(map));
        put(cmds, "plot.rent", new PlotRentCmd(map, economy));
        put(cmds, "plot.rentout", new PlotRentoutCmd(map));
        put(cmds, "plot.reserve", new PlotReserveCmd(map));
        return new PlotCmd(map, new CmdHandler(cmds));
    }
}
