package de.bloodeko.towns.cmds;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Services;
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
import de.bloodeko.towns.town.TownCmd;
import de.bloodeko.towns.town.settings.cmds.ExtensionCmd;
import de.bloodeko.towns.town.settings.cmds.ExtensionsCmd;
import de.bloodeko.towns.town.settings.cmds.RenameCmd;
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
import de.bloodeko.towns.town.settings.stage.StageFactory;
import de.bloodeko.towns.town.settings.stage.ui.StageCmd;
import de.bloodeko.towns.util.Messages;

public class CmdFactory {
    
    public static void load() {
        Towns plugin = Services.plugin();
        CmdHandler handler = newCmdHandler(plugin);
        TownCmd cmd = new TownCmd(handler);
        plugin.getCommand("stadt").setExecutor(cmd);
        plugin.getCommand("stadt").setTabCompleter(cmd);
        
        PlotCmd plotCmd = newPlotCmd();
        plugin.getCommand("gs").setExecutor(plotCmd);
        plugin.getCommand("gs").setTabCompleter(plotCmd);
    }
    
    /**
     * Uses the id to get a translated cmd name under "cmds.id"
     * registers it with the CmdBase to the town command.
     */
    public static void put(Map<String, CmdBase> cmds, String id, CmdBase base) {
        cmds.put(Messages.get("cmds." + id), base);
    }
    
    private static CmdHandler newCmdHandler(Towns plugin) {
        Map<String, CmdBase> cmds = new HashMap<>();
        
        put(cmds, "map", new MapCmd(MapFactory.newClickHandler(plugin)));
        put(cmds, "info", new InfoCmd());
        put(cmds, "settings", new SettingsCmd());
        put(cmds, "test", new TestCmd());
        
        put(cmds, "claim", new ClaimCmd());
        put(cmds, "unclaim", new UnclaimCmd());
        
        put(cmds, "builder", new BuilderCmd());
        put(cmds, "governor", new GovenorCmd());
        put(cmds, "owner", new OwnerCmd());
        put(cmds, "found", new FoundCmd());
        put(cmds, "delete", new DeleteCmd());

        put(cmds, "extensions", new ExtensionsCmd());
        put(cmds, "extension", new ExtensionCmd());
        StageFactory.load(cmds);
        
        put(cmds, "tp", new TpCmd());
        put(cmds, "rename", new RenameCmd());
        put(cmds, "stage", new StageCmd());
        put(cmds, "warp", new WarpCmd());
        put(cmds, "pvp", new PvpProtectCmd(Messages.get("settings.pvp")));
        put(cmds, "damageAnimals", new AnimalProtectCmd(Messages.get("settings.damageAnimals")));
        put(cmds, "warp", new WarpCmd());
        put(cmds, "pearl", new PearlCmd(Messages.get("settings.pearl")));
        put(cmds, "slime", new SlimeCmd(Messages.get("settings.slime")));
        put(cmds, "zombie", new ZombieCmd(Messages.get("settings.zombie")));
        
        return new CmdHandler(cmds);
    }
    
    private static PlotCmd newPlotCmd() {
        Map<String, CmdBase> cmds = new HashMap<>();
        put(cmds, "plot.builder", new PlotBuilderCmd());
        put(cmds, "plot.create", new PlotCreateCmd());
        put(cmds, "plot.expropriate", new PlotExpropriateCmd());
        put(cmds, "plot.info", new PlotInfoCmd());
        put(cmds, "plot.leave", new PlotLeaveCmd());
        put(cmds, "plot.list", new PlotListCmd());
        put(cmds, "plot.name", new PlotNameCmd());
        put(cmds, "plot.remove", new PlotRemoveCmd());
        put(cmds, "plot.rent", new PlotRentCmd());
        put(cmds, "plot.rentout", new PlotRentoutCmd());
        put(cmds, "plot.reserve", new PlotReserveCmd());
        return new PlotCmd(new CmdHandler(cmds));
    }
}
