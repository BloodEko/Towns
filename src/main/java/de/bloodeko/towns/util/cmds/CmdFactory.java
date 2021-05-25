package de.bloodeko.towns.util.cmds;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.Towns;
import de.bloodeko.towns.core.townarea.ui.ClaimCmd;
import de.bloodeko.towns.core.townarea.ui.UnclaimCmd;
import de.bloodeko.towns.core.townarea.ui.map.MapCmd;
import de.bloodeko.towns.core.townarea.ui.map.MapFactory;
import de.bloodeko.towns.core.townnames.ui.RenameCmd;
import de.bloodeko.towns.core.townpeople.ui.BuilderCmd;
import de.bloodeko.towns.core.townpeople.ui.GovenorCmd;
import de.bloodeko.towns.core.townpeople.ui.OwnerCmd;
import de.bloodeko.towns.core.townplots.ui.PlotBuilderCmd;
import de.bloodeko.towns.core.townplots.ui.PlotCmd;
import de.bloodeko.towns.core.townplots.ui.PlotCreateCmd;
import de.bloodeko.towns.core.townplots.ui.PlotExpropriateCmd;
import de.bloodeko.towns.core.townplots.ui.PlotInfoCmd;
import de.bloodeko.towns.core.townplots.ui.PlotLeaveCmd;
import de.bloodeko.towns.core.townplots.ui.PlotListCmd;
import de.bloodeko.towns.core.townplots.ui.PlotNameCmd;
import de.bloodeko.towns.core.townplots.ui.PlotRemoveCmd;
import de.bloodeko.towns.core.townplots.ui.PlotRentCmd;
import de.bloodeko.towns.core.townplots.ui.PlotRentoutCmd;
import de.bloodeko.towns.core.townplots.ui.PlotReserveCmd;
import de.bloodeko.towns.core.towns.ui.DeleteCmd;
import de.bloodeko.towns.core.towns.ui.FoundCmd;
import de.bloodeko.towns.core.towns.ui.InfoCmd;
import de.bloodeko.towns.core.towns.ui.TestCmd;
import de.bloodeko.towns.core.townsettings.legacy.cmds.ExtensionCmd;
import de.bloodeko.towns.core.townsettings.legacy.cmds.ExtensionsCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.DamageAnimalsSetting.AnimalProtectCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.PearlSetting.PearlCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.PvpSetting.PvpProtectCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.SlimeSetting.SlimeCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.WarpSetting.WarpCmd;
import de.bloodeko.towns.core.townsettings.legacy.general.ZombieSetting.ZombieCmd;
import de.bloodeko.towns.core.townsettings.ui.SettingsCmd;
import de.bloodeko.towns.core.townsettings.ui.TpCmd;
import de.bloodeko.towns.core.townstages.StageFactory;
import de.bloodeko.towns.core.townstages.ui.StageCmd;
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
