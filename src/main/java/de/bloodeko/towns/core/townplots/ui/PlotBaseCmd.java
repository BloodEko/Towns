package de.bloodeko.towns.core.townplots.ui;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.PlotHandler;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

public abstract class PlotBaseCmd extends CmdBase {
    
    /**
     * Returns the PlotHandler for that town.
     * Throws an exception if it is not bought.
     */
    public PlotHandler getPlotHandler(Town town) {
        PlotHandler handler = town.getPlots();
        if (handler == null) {
            throw new ModifyException("settings.plot.basecmd.settingNotBought");
        }
        return handler;
    }

    /**
     * Gets the plot at the players Location 
     * or throws an exception.
     */
    public PlotData getPlotAsPlayer(Player player) {
        Town town = getTownAsPlayer(player);
        String plotId = getPlotID(player);
        if (plotId == null) {
            throw new ModifyException("settings.plot.basecmd.noPlotAtLocation");
        }
        PlotHandler handler = town.getPlots();
        return handler.getPlot(Integer.valueOf(plotId));
    }
    
    /**
     * Gets the plot. Throws an exception if the player 
     * is no governor or no plot was found.
     */
    public PlotData getPlotAsGovernor(Player player) {
        getTown(player);
        return getPlotAsPlayer(player);
    }

    /**
     * Gets the plot. Throws an exception if the player is
     * no renter or governor or no plot was found.
     */
    public PlotData getPlotAsRenter(Player player) {
        PlotData plot = getPlotAsPlayer(player);
        if (plot.renter == null) {
            getTown(player);
            return plot;
        }
        if (!player.getUniqueId().equals(plot.renter)) {
            throw new ModifyException("settings.plot.basecmd.notYourPlot");
        }
        return plot;
    }
    
    /**
     * Checks if the player has enough money. Otherwise throws an exception.
     * Pays the price to the towns first found governor.
     */
    public void payRentToTown(Player player, int price, Town town) {
        checkMoney(player, price);
        UUID toUUID = town.getPeople().getGovernors().iterator().next();
        OfflinePlayer toPlayer = Bukkit.getOfflinePlayer(toUUID);
        Services.economy().withdrawPlayer(player, price);
        Services.economy().depositPlayer(toPlayer, price);
    }
    
    /**
     * Gets the plotID at the players location or null.
     */
    public String getPlotID(Player player) {
        BlockVector3 at = locationToVector(player.getLocation());
        for (String id : getRegionManager().getApplicableRegionsIDs(at)) {
            if (id.startsWith("plot_")) {
                return id.split("_")[2];
            }
        }
        return null;
    }
    
    /**
     * Gets the WE plugin.
     */
    public WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }
    
    public Region getSelection(Player player) {
        RegionSelector selector = getWorldEdit().getSession(player)
          .getRegionSelector(BukkitAdapter.adapt(player.getWorld()));
        return selector.getIncompleteRegion();
    }
    
    public void checkIsComplete(BlockVector3 pos1, BlockVector3 pos2) {
        if (BlockVector3.ZERO.equals(pos1) || BlockVector3.ZERO.equals(pos2)) {
            throw new ModifyException("Region is not complete!");
        }
    }
    
    public BlockVector3 locationToVector(Location loc) {
        return BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    
    /**
     * Gets the rg manager for "world".
     */
    public static RegionManager getRegionManager() {
        return Services.regionmanager();
    }
    
    /**
     * Returns the regions that intersect with the cuboid
     * of pos1 and pos2.
     */
    public static ApplicableRegionSet getRegions(Player player, BlockVector3 pos1, BlockVector3 pos2) {
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("temp", true, pos1, pos2);
        return getRegionManager().getApplicableRegions(region);
    }
}
