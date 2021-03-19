package de.bloodeko.towns.cmds.general;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.world.World;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

public class PlotCmd extends CmdBase {

    public PlotCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        World world = BukkitAdapter.adapt(player.getWorld());
        RegionSelector selector = getWorldEdit().getSession(player).getRegionSelector(world);
        Region region = selector.getIncompleteRegion();
        BlockVector3 pos1 = region.getMinimumPoint();
        BlockVector3 pos2 = region.getMaximumPoint();
        
        verifyComplete(pos1, pos2);
        Chunk ch1 = Chunk.fromCoords(pos1.getX(), pos1.getZ());
        Chunk ch2 = Chunk.fromCoords(pos2.getX(), pos2.getZ());
        
        verifySize(ch1, ch2, 8);
        printChunks(player, Chunk.getChunksWithin(ch1, ch2));
    }
    
    public void verifyComplete(BlockVector3 pos1, BlockVector3 pos2) {
        if (BlockVector3.ZERO.equals(pos1) || BlockVector3.ZERO.equals(pos2)) {
            throw new ModifyException("Region is not complete!");
        }
    }
    
    public void verifySize(Chunk a, Chunk b, int maxChunks) {
        int aa = Math.min(a.x, b.x) - Math.max(a.x, b.x);
        int bb = Math.min(a.z, b.z) - Math.max(a.z, b.z);
        if (aa * bb > maxChunks) {
            throw new ModifyException("Max chunk size: " + maxChunks);
        }
    }
    
    public void printChunks(Player player, Chunk[] chunks) {
        for (Chunk chunk : chunks) {
            Town town = getMap().query(chunk);
            if (town == null) {
                player.sendMessage(chunk + ": null");
            } else {
                player.sendMessage(chunk + ": " + town.getSettings().getName());
            }
        }
    }
    
    public WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }
}
